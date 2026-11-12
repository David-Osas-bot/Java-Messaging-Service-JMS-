import { useState } from 'react'
import { eventInfo, formatTime } from './utils.js'

const FILTERS = [
    { label: 'All', value: 'ALL' },
    { label: 'Logins', value: 'USER_LOGGED_IN' },
    { label: 'Logouts', value: 'USER_LOGGED_OUT' },
]

export default function ActivityPanel({ notifications }) {
    const [filter, setFilter] = useState('ALL')

    const shown =
        filter === 'ALL' ? notifications : notifications.filter((n) => n.eventType === filter)

    return (
        <section>
            <div className="mb-4 flex items-center justify-between gap-3">
                <h2 className="font-serif text-lg text-gray-900">Recent activity</h2>
                <div className="flex gap-1 rounded-lg bg-gray-100 p-1 text-sm">
                    {FILTERS.map((f) => (
                        <button
                            key={f.value}
                            onClick={() => setFilter(f.value)}
                            className={`rounded-md px-3 py-1 ${filter === f.value ? 'bg-white font-medium shadow-sm' : 'text-gray-600'}`}
                        >
                            {f.label}
                        </button>
                    ))}
                </div>
            </div>

            <div className="rounded-xl border border-gray-200">
                {shown.map((n) => {
                    const info = eventInfo(n.eventType)
                    return (
                        <div key={n.id} className="flex items-center justify-between gap-3 border-b border-gray-100 px-4 py-3 last:border-b-0">
                            <div className="flex items-center gap-3">
                                <span className={`h-2.5 w-2.5 rounded-full ${info.dot}`} />
                                <div>
                                    <p className="text-sm text-gray-900">{n.fullName}</p>
                                    <p className="text-xs text-gray-500">{n.email}</p>
                                </div>
                            </div>
                            <div className="text-right">
                                <span className={`rounded-full px-2.5 py-1 text-xs font-medium ${info.badge}`}>
                                    {info.text}
                                </span>
                                <p className="mt-1 text-xs text-gray-400">{formatTime(n.occurredAt)}</p>
                            </div>
                        </div>
                    )
                })}
                {shown.length === 0 && (
                    <p className="px-4 py-8 text-center text-sm text-gray-400">No activity yet</p>
                )}
            </div>
        </section>
    )
}

