import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthLayout from '../components/AuthLayout.jsx'
import Field from '../components/Field.jsx'

// CHANGE THIS to your real login endpoint
const LOGIN_URL = '/messaging/api/auth/login'

export default function Login() {
    const navigate = useNavigate()

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    async function handleSubmit(e) {
        e.preventDefault() // stop the page from reloading
        setError('')
        setLoading(true)

        try {
            const res = await fetch(LOGIN_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password }),
            })

            if (!res.ok) {
                setError('Invalid email or password')
                return
            }

            const data = await res.json()
            localStorage.setItem('user', JSON.stringify(data)) // remember who logged in
            navigate('/dashboard')
        } catch (err) {
            setError('Could not reach the server. Is GlassFish running?')
        } finally {
            setLoading(false)
        }
    }

    return (
        <AuthLayout title="Sign into your account" subtitle="Manage your users and notifications.">
            <form onSubmit={handleSubmit}>
                <Field label="Email address" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                <Field label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />

                <a href="#" className="mb-5 inline-block text-xs font-medium text-green-600">
                    Forgot password?
                </a>

                {error && <p className="mb-3 text-sm text-red-600">{error}</p>}

                <button
                    type="submit"
                    disabled={loading}
                    className="w-full rounded-lg bg-black py-2.5 text-sm font-medium text-white hover:bg-gray-800 disabled:opacity-60"
                >
                    {loading ? 'Signing in...' : 'Sign in'}
                </button>

                {/* Visual only for now - needs extra setup to work */}
                <button
                    type="button"
                    className="mt-3 w-full rounded-lg border border-gray-300 py-2.5 text-sm text-gray-800 hover:bg-gray-50"
                >
                    Sign in with Google
                </button>

                <p className="mt-5 text-xs text-gray-500">
                    Don't have an account?{' '}
                    <Link to="/register" className="font-medium text-green-600">Signup</Link>
                </p>
            </form>
        </AuthLayout>
    )
}