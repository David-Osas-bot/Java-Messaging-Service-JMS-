// Turns an event type from the database into friendly text and colours
export function eventInfo(type) {
    switch (type) {
        case 'USER_LOGGED_IN':
            return { text: 'logged in', dot: 'bg-green-500', badge: 'bg-green-50 text-green-700' }
        case 'USER_LOGGED_OUT':
            return { text: 'logged out', dot: 'bg-gray-400', badge: 'bg-gray-100 text-gray-700' }
        case 'USER_REGISTERED':
            return { text: 'registered', dot: 'bg-blue-500', badge: 'bg-blue-50 text-blue-700' }
        default:
            return { text: type, dot: 'bg-gray-300', badge: 'bg-gray-100 text-gray-700' }
    }
}

export function formatTime(iso) {
    return new Date(iso).toLocaleString()
}