import React, { useEffect, useState, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { createOrder, fetchProducts, setOrder } from './productSlice';
import ProductCard from '../../component/ProductCard';
import { formatMoney } from '../../util';
import Modal from '../../component/Modal';

const ENTER_KEYCODE = 13;
const AVAILABLE_PRICE = [10000, 20000, 50000, 100000, 200000];
const INVALID_MONEY_MSG = "Invalid money";
const PLS_SELECT_A_PRODUCT_MSG = "Please select a product!";
const MONEY_NOT_ENOUGH_MSG = "Money entered is not enough!"

const Product = () => {
    const dispatch = useDispatch();
    const { products, order, status, error } = useSelector((state) => state.products);

    const [selectedProducts, setSelectedProducts] = useState([]);
    const [currentMoney, setCurrentMoney] = useState(0);

    const inputMoneyRef = useRef();
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        if (status === 'idle') {
            dispatch(fetchProducts());
        }
    }, [status, dispatch]);

    const handleChangeQuantity = (productId, newQuantity) => {
        if (newQuantity <= 0) {
            setSelectedProducts(selectedProducts.filter(item => item.id !== productId));
        }
        const newSelectedProducts = structuredClone(selectedProducts);
        const changedProductIdx = newSelectedProducts.findIndex(item => item.id === productId);
        if (changedProductIdx > -1) {
            newSelectedProducts[changedProductIdx].quantity = newQuantity;
        } else {
            newSelectedProducts.push({
                id: productId,
                quantity: newQuantity,
                price: products.find(item => item.id === productId)?.productPrice || 0
            })
        }

        setSelectedProducts(newSelectedProducts);
        setErrMsg("");
    }

    const handleChangeQuantityByButton = (productId, changeValue) => {
        const changedProductIdx = selectedProducts.findIndex(item => item.id === productId);
        if (changedProductIdx > -1) {
            handleChangeQuantity(productId, Number(selectedProducts[changedProductIdx].quantity) + changeValue);
        } else if (changeValue === 1) {
            handleChangeQuantity(productId, 1)
        }
    }

    let productList;

    if (status === 'loading') {
        productList = <p>Loading...</p>;
    } else if (status === 'succeeded') {
        productList = (
            <div className='flex gap-8'>
                {products.map(product =>
                    <ProductCard
                        key={product.id}
                        productData={product}
                        currentQuantity={
                            selectedProducts.find(
                                item => item.id === product.id
                            )?.quantity || ""
                        }
                        handleChangeQuantityByButton={changeValue => { handleChangeQuantityByButton(product.id, changeValue) }}
                        handleChangeQuantityByInput={newQuantity => { handleChangeQuantity(product.id, newQuantity) }}
                    />
                )}
            </div>
        );
    } else if (status === 'failed') {
        productList = <p>{error}</p>;
    }

    const handleOrderBtnClick = () => {
        const selectedPrice = selectedProducts
            .reduce(
                (total, item) => total + item.quantity * item.price, 0);
        if (selectedPrice === 0) {
            setErrMsg(PLS_SELECT_A_PRODUCT_MSG);
            return;
        }
        if (selectedPrice > currentMoney) {
            setErrMsg(MONEY_NOT_ENOUGH_MSG);
            return;
        }
        const order = {
            orderDetails: selectedProducts.map(item => ({
                productId: item.id,
                price: item.price,
                quantity: item.quantity
            })),
            totalMoney: Number(currentMoney)
        }
        dispatch(createOrder(order));
    }

    const handleCloseModal = () => {
        dispatch(setOrder(null));
        resetOrder();
    }

    const resetOrder = () => {
        setSelectedProducts([]);
        setCurrentMoney(0);
        inputMoneyRef.current.value = "";
    }

    const handleKeyDown = e => {
        try {
            const keyCode = e.keyCode;
            const value = e.target.value;
            if (keyCode === ENTER_KEYCODE) {
                handleEnterMoney(Number(value));
            }
        } catch (e) {
            console.error(e);
        }
    }

    const handleEnterMoney = value => {
        if (AVAILABLE_PRICE.includes(value)) {
            setCurrentMoney(currentMoney + value);
            inputMoneyRef.current.value = "";
        } else {
            //
            inputMoneyRef.current.focus();
            setErrMsg(INVALID_MONEY_MSG);
        }
    }

    const handleInputMoneyChange = e => {
        setErrMsg("");
    }

    return (
        <>
            {productList}
            <div className='pt-10 flex items-center justify-start'>
                Selected prices: {formatMoney(
                    selectedProducts
                        .reduce(
                            (total, item) => total + item.quantity * item.price, 0)
                )
                }
            </div>

            <div className='pt-10 flex items-center justify-start gap-2'>
                Input Money:
                <input
                    className='border rounded'
                    type='number'
                    ref={inputMoneyRef}
                    onKeyDown={handleKeyDown}
                    onChange={handleInputMoneyChange}
                />
                <button
                    className='w-24'
                    onClick={() => {
                        const value = Number(inputMoneyRef.current.value);
                        if (!value || value <= 0) return;
                        handleEnterMoney(value);
                    }}
                >
                    Enter
                </button>
            </div>

            <div className='pt-4 flex items-center justify-start'>
                Money entered: {formatMoney(currentMoney)}
            </div>

            <div className='p-2 text-red-500'>
                {errMsg}
            </div>

            <div className='flex-center gap-2'>
                <button
                    className='w-24'
                    onClick={resetOrder}
                >
                    Reset
                </button>
                <button
                    className='bg-blue-500 text-white w-20'
                    onClick={handleOrderBtnClick}
                >
                    Buy
                </button>
            </div>

            {order && (
                <Modal onClose={handleCloseModal}>
                    <div className='flex flex-col gap-2'>
                        <div className='font-extrabold text-lg'>Transaction successful</div>

                        <div className='product-list flex flex-col'>
                            {order.orderDetails.map((item, index) => (
                                <div key={index} className='flex-center gap-2 border-y'>
                                    <div className="w-24 p-1 h-24 flex-center bg-white rounded">
                                        <img src={item.image} />
                                    </div>
                                    <div>
                                        <div className="flex items-center justify-start font-bold">Name: {item.productName}</div>
                                        <div className="flex items-center justify-start font-bold">Price: {formatMoney(item.price)}</div>
                                        <div className="flex items-center justify-start font-bold">Quantity: {item.quantity}</div>
                                    </div>

                                </div>
                            ))}
                        </div>

                        <div>Remain Money: {formatMoney(order.remainMoney)}</div>
                        {order.freeProducts?.length > 0 && (
                            <div className='p-10'>
                                You have received a promotion:
                                {order.freeProducts.map(item => <div key={item}> 1 {item} </div>)}
                            </div>
                        )}

                        <div className='flex-center'>
                            <button
                                className='w-24 bg-blue-500 text-white'
                                onClick={handleCloseModal}
                            >
                                OK
                            </button>
                        </div>


                    </div>
                </Modal>
            )}
        </>
    )
}

export default Product;