const Modal = ({ onClose, children }) => {
    return (
        <div className="fixed inset-0 z-50 flex-center bg-black bg-opacity-50 text-gray-900">
            <div className="relative w-full max-w-lg p-6 bg-white rounded-lg shadow-lg">
                {/* Nút đóng */}
                <button
                    onClick={onClose}
                    className="absolute top-2 right-2 text-gray-600 hover:text-gray-900 w-4 h-4 flex-center"
                >
                    &times;
                </button>

                <div>{children}</div>
            </div>
        </div>
    );
}

export default Modal;