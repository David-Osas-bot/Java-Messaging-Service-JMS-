import { useState } from 'react'
import { USERS_URL } from './api.js'
import EditUserModal from './EditUserModal.jsx'

export default function UsersPanel({ users, error, onChanged }) {
    const [search, setSearch] = useState('')
    const [editing, setEditing] = useState(null) // the user being edited

    const shown = users.filter((u) =>
        `${u.fullName} ${u.email} ${u.phone}`.toLowerCase().includes(search.toLowerCase())
    )

    async function handleDelete(user) {
        if (!window.confirm(`Delete ${user.fullName}?`)) return

        const res = await fetch(`${USERS_URL}/${user.id}`, { method: 'DELETE' })
        if (res.ok) {
            onChanged() // reload the list
        } else {
            alert('Could not delete this user.')
        }
    }

    return (
        <section>
            <div className="mb-4 flex items-center justify-between gap-3">
                <h2 className="font-serif text-lg text-gray-900">Users</h2>
                <input
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    placeholder="Search users..."
                    className="w-64 rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-green-600 focus:ring-1 focus:ring-green-600"
                />
            </div>

            {error && <p className="mb-3 text-sm text-red-600">{error}</p>}

            <div className="overflow-x-auto rounded-xl border border-gray-200">
                <table className="w-full text-left text-sm">
                    <thead className="bg-gray-50 text-xs text-gray-500">
                        <tr>
                            <th className="px-4 py-3 font-medium">Name</th>
                            <th className="px-4 py-3 font-medium">Email</th>
                            <th className="px-4 py-3 font-medium">Phone</th>
                            <th className="px-4 py-3 text-right font-medium">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {shown.map((u) => (
                            <tr key={u.id} className="border-t border-gray-100">
                                <td className="px-4 py-3 text-gray-900">{u.fullName}</td>
                                <td className="px-4 py-3 text-gray-600">{u.email}</td>
                                <td className="px-4 py-3 text-gray-600">{u.phone}</td>
                                <td className="px-4 py-3 text-right">
                                    <button onClick={() => setEditing(u)} className="mr-4 font-medium text-green-600">
                                        Edit
                                    </button>
                                    <button onClick={() => handleDelete(u)} className="font-medium text-red-600">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        {shown.length === 0 && (
                            <tr>
                                <td colSpan="4" className="px-4 py-8 text-center text-gray-400">
                                    No users found
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {editing && (
                <EditUserModal
                    user={editing}
                    onClose={() => setEditing(null)}
                    onSaved={() => {
                        setEditing(null)
                        onChanged()
                    }}
                />
            )}
        </section>
    )
}