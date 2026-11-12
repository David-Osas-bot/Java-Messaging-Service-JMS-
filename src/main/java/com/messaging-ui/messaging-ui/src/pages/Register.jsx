import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthLayout from '../components/AuthLayout.jsx'
import Field from '../components/Field.jsx'

// CHANGE THIS to your real register endpoint
const REGISTER_URL = 'messaging/api/users/create'

export default function Register() {
    const navigate = useNavigate()

    const [fullName, setFullName] = useState('')
    const [phone, setPhone] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    async function handleSubmit(e) {
        e.preventDefault()
        setError('')
        setLoading(true)

        try {
            const res = await fetch(REGISTER_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ fullName, phone, email, password }),
            })

            if (!res.ok) {
                setError('Registration failed. This email may already be in use.')
                return
            }

            navigate('/login') // account created, go and sign in
        } catch (err) {
            setError('Could not reach the server. Is GlassFish running?')
        } finally {
            setLoading(false)
        }
    }

    return (
        <AuthLayout title="Create your account" subtitle="Sign up to get started.">
            <form onSubmit={handleSubmit}>
                <Field label="Full name" value={fullName} onChange={(e) => setFullName(e.target.value)} />
                <Field label="Phone" type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} />
                <Field label="Email address" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                <Field label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />

                {error && <p className="mb-3 text-sm text-red-600">{error}</p>}

                <button
                    type="submit"
                    disabled={loading}
                    className="mt-2 w-full rounded-lg bg-black py-2.5 text-sm font-medium text-white hover:bg-gray-800 disabled:opacity-60"
                >
                    {loading ? 'Creating account...' : 'Sign up'}
                </button>

                <p className="mt-5 text-xs text-gray-500">
                    Already have an account?{' '}
                    <Link to="/login" className="font-medium text-green-600">Sign in</Link>
                </p>
            </form>
        </AuthLayout>
    )
}