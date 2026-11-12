import { useState } from 'react'
import { USERS_URL, UPDATE_USER_URL } from '../components/api.js'
import Field from './Field.jsx'

export default function EditUserModal({ user, onClose, onSaved }) {
    const [fullName, setFullName] = useState(user.fullName || '')
    const [phone, setPhone] = useState(user.phone || '')
    const [email, setEmail] = useState(user.email || '')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    async function handleSubmit(e) {
        e.preventDefault()
        setError('')
        setLoading(true)

        try {
            const res = await fetch(`${UPDATE_USER_URL}/${user.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ fullName, phone, email }),
            })

            if (!res.ok) {
                setError('Could not update this user.')
                return
            }
            onSaved()
        } catch {
            setError('Could not reach the server.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="fixed inset-0 z-40 flex items-center justify-center bg-black/40 p-4">
            <form onSubmit={handleSubmit} className="w-full max-w-sm rounded-xl bg-white p-6 shadow-xl">
                <h2 className="mb-4 font-serif text-xl text-gray-900">Edit user</h2>

                <Field label="Full name" value={fullName} onChange={(e) => setFullName(e.target.value)} />
                <Field label="Phone" type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} />
                <Field label="Email address" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />

                {error && <p className="mb-3 text-sm text-red-600">{error}</p>}

                <div className="flex gap-3">
                    <button
                        type="button"
                        onClick={onClose}
                        className="w-full rounded-lg border border-gray-300 py-2.5 text-sm text-gray-800 hover:bg-gray-50"
                    >
                        Cancel
                    </button>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full rounded-lg bg-black py-2.5 text-sm font-medium text-white hover:bg-gray-800 disabled:opacity-60"
                    >
                        {loading ? 'Saving...' : 'Save'}
                    </button>
                </div>
            </form>
        </div>
    )
}