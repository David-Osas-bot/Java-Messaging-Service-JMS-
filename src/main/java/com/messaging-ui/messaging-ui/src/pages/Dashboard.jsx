import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { USERS_URL, NOTIFICATIONS_URL, LOGOUT_URL } from '../components/api.js'
import { eventInfo } from '../components/utils.js'
import UsersPanel from '../components/UsersPanel.jsx'
import ActivityPanel from '../components/ActivityPanel.jsx'

export default function Dashboard() {
    const navigate = useNavigate()

    const [tab, setTab] = useState('users') // 'users' or 'activity'
    const [users, setUsers] = useState([])
    const [usersError, setUsersError] = useState('')
    const [notifications, setNotifications] = useState([])
    const [toasts, setToasts] = useState([])

    // remembers which notifications we have already seen (null = first load)
    const seenIds = useRef(null)

    const admin = JSON.parse(localStorage.getItem('user') || 'null')

    // If nobody is logged in, send them to the login page
    useEffect(() => {
        if (!admin) navigate('/login')
    }, [])

    // ---------- USERS ----------
    async function loadUsers() {
        try {
            const res = await fetch(USERS_URL)
            if (!res.ok) throw new Error()
            setUsers(await res.json())
            setUsersError('')
        } catch {
            setUsersError('Could not load users. Check USERS_URL in src/api.js')
        }
    }

    // ---------- NOTIFICATIONS (checked every 5 seconds) ----------
    async function loadNotifications() {
        try {
            const res = await fetch(NOTIFICATIONS_URL)
            if (!res.ok) return
            const data = await res.json()

            if (seenIds.current === null) {
                // first load: just remember everything, no pop-ups
                seenIds.current = new Set(data.map((n) => n.id))
            } else {
                const fresh = data.filter((n) => !seenIds.current.has(n.id))
                fresh.forEach((n) => {
                    seenIds.current.add(n.id)
                    showToast(n)
                })
            }
            setNotifications(data)
        } catch {
            // server temporarily unreachable, try again on the next check
        }
    }

    function showToast(n) {
        setToasts((old) => [...old, n])
        setTimeout(() => {
            setToasts((old) => old.filter((t) => t.id !== n.id))
        }, 5000)
    }

    useEffect(() => {
        loadUsers()
        loadNotifications()
        const timer = setInterval(loadNotifications, 5000)
        return () => clearInterval(timer) // stop checking when leaving the page
    }, [])

    // ---------- LOGOUT ----------
    async function handleLogout() {
        try {
            // tells the backend so it can create a USER_LOGGED_OUT notification
            await fetch(LOGOUT_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email: admin?.email }),
            })
        } catch {
            // ignore, we still log out on this side
        }
        localStorage.removeItem('user')
        navigate('/login')
    }

    const loginCount = notifications.filter((n) => n.eventType === 'USER_LOGGED_IN').length
    const logoutCount = notifications.filter((n) => n.eventType === 'USER_LOGGED_OUT').length

    return (
        <div className="min-h-screen bg-gray-50 p-3">
            <div className="min-h-[calc(100vh-1.5rem)] rounded-xl border border-gray-200 bg-white">

                {/* TOP BAR */}
                <header className="flex flex-wrap items-center justify-between gap-3 border-b border-gray-200 px-6 py-4">
                    <div className="flex items-center gap-3">
                        <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-green-600 font-bold text-white">
                            M
                        </div>
                        <span className="font-serif text-lg text-gray-900">Admin Dashboard</span>
                    </div>

                    <nav className="flex gap-1 rounded-lg bg-gray-100 p-1 text-sm">
                        <button
                            onClick={() => setTab('users')}
                            className={`rounded-md px-4 py-1.5 ${tab === 'users' ? 'bg-white font-medium shadow-sm' : 'text-gray-600'}`}
                        >
                            Users
                        </button>
                        <button
                            onClick={() => setTab('activity')}
                            className={`rounded-md px-4 py-1.5 ${tab === 'activity' ? 'bg-white font-medium shadow-sm' : 'text-gray-600'}`}
                        >
                            Activity
                        </button>
                    </nav>

                    <button
                        onClick={handleLogout}
                        className="rounded-lg bg-black px-4 py-2 text-sm font-medium text-white hover:bg-gray-800"
                    >
                        Log out
                    </button>
                </header>

                <main className="p-6 md:p-8">
                    <h1 className="font-serif text-2xl text-gray-900">
                        Welcome{admin?.fullName ? `, ${admin.fullName}` : ''}
                    </h1>
                    <p className="mt-1 mb-6 text-sm text-gray-500">
                        Manage your users and see who is coming and going.
                    </p>

                    {/* STAT CARDS */}
                    <div className="mb-8 grid gap-4 sm:grid-cols-3">
                        <StatCard label="Total users" value={users.length} />
                        <StatCard label="Logins" value={loginCount} />
                        <StatCard label="Logouts" value={logoutCount} />
                    </div>

                    {tab === 'users' ? (
                        <UsersPanel users={users} error={usersError} onChanged={loadUsers} />
                    ) : (
                        <ActivityPanel notifications={notifications} />
                    )}
                </main>
            </div>

            {/* POP-UP NOTIFICATIONS (top right) */}
            <div className="fixed top-4 right-4 z-50 flex w-80 flex-col gap-2">
                {toasts.map((t) => {
                    const info = eventInfo(t.eventType)
                    return (
                        <div key={t.id} className="flex items-start gap-3 rounded-lg border border-gray-200 bg-white p-3 shadow-lg">
                            <span className={`mt-1.5 h-2.5 w-2.5 shrink-0 rounded-full ${info.dot}`} />
                            <div className="text-sm">
                                <p className="font-medium text-gray-900">{t.fullName}</p>
                                <p className="text-gray-500">{info.text}</p>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

function StatCard({ label, value }) {
    return (
        <div className="rounded-xl border border-gray-200 p-5">
            <p className="text-xs text-gray-500">{label}</p>
            <p className="mt-1 font-serif text-3xl text-gray-900">{value}</p>
        </div>
    )
}