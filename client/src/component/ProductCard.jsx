import { formatMoney } from "../util";

const ProductCard = ({ productData, currentQuantity, handleChangeQuantityByButton, handleChangeQuantityByInput }) => {
    const { productName, productPrice, productImage } = productData;
    return (
        <div>
            <div className="info">
                <div className="w-36 p-1 h-36 flex-center bg-white rounded">
                    <img src={productImage} />
                </div>
                <div className="flex-center font-bold">{productName}</div>
                <div className="flex-center font-semibold text-gray-400">{formatMoney(productPrice)}</div>
            </div>

            <div className="handle-quantity flex-center gap-2">
                <button
                    className="decrease rounded cursor-pointer w-6 h-7 flex-center"
                    onClick={() => handleChangeQuantityByButton(-1)}
                    disabled={currentQuantity < 1}
                > - </button>
                <div>
                    <input
                        type="number"
                        value={currentQuantity}
                        className="border rounded w-20"
                        onChange={e => handleChangeQuantityByInput(e.target.value)}
                    />
                </div>
                <button
                    className="decrease rounded cursor-pointer w-6 h-7 flex-center"
                    onClick={() => handleChangeQuantityByButton(1)}
                > + </button>
            </div>
        </div>
    )
}

export default ProductCard;