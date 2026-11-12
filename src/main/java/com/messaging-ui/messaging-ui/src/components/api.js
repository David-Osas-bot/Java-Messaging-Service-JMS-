// All backend URLs live here. If something returns 404, fix it in this file only.
const BASE = '/messaging/api'

export const USERS_URL = `${BASE}/users/getAll` 
export const UPDATE_USER_URL = `${BASE}/users`            // PUT /{id}, DELETE /{id}
export const NOTIFICATIONS_URL = `${BASE}/notifications`  // GET (all)
export const LOGOUT_URL = `${BASE}/users/logout`          // optional, see notes