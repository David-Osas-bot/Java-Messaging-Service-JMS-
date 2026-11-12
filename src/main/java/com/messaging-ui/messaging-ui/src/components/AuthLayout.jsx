// Shared layout: form on the left, picture on the right.
// Put your image at: public/auth-image.png
export default function AuthLayout({ title, subtitle, children }) {
    return (
        <div className="min-h-screen bg-gray-50 p-3">
            <div className="flex min-h-[calc(100vh-1.5rem)] overflow-hidden rounded-xl border border-gray-200 bg-white">

                {/* LEFT: form */}
                <div className="flex w-full items-center justify-center px-8 py-10 lg:w-[38%]">
                    <div className="w-full max-w-sm">
                        <div className="mb-4 flex h-10 w-10 items-center justify-center rounded-lg bg-green-600 text-lg font-bold text-white">
                            M
                        </div>
                        <h1 className="font-serif text-2xl text-gray-900">{title}</h1>
                        <p className="mt-1 mb-6 text-sm text-gray-500">{subtitle}</p>
                        {children}
                    </div>
                </div>

                {/* RIGHT: image (hidden on small screens) */}
                <div
                    className="hidden flex-1 bg-teal-600 bg-cover bg-center lg:block"
                    style={{ backgroundImage: "url('/auth-image.png')" }}
                />
            </div>
        </div>
    )
}