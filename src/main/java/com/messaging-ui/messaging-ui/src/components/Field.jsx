// One labelled input box, used by both pages
export default function Field({ label, type = 'text', value, onChange, placeholder }) {
    return (
        <div className="mb-4">
            <label className="mb-1 block text-xs font-medium text-gray-800">
                {label} <span className="text-red-500">*</span>
            </label>
            <input
                type={type}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                required
                className="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm outline-none focus:border-green-600 focus:ring-1 focus:ring-green-600"
            />
        </div>
    )
}