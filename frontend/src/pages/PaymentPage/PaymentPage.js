import { Elements } from '@stripe/react-stripe-js';
import React, { useEffect, useState } from 'react'
import CheckoutForm from './CheckoutPayment';
import { loadStripe } from '@stripe/stripe-js';
import { useDispatch, useSelector } from 'react-redux';
import { setLoading } from '../../store/features/common';
import { fetchUserDetails } from '../../api/userInfo';
import { selectCartItems } from '../../store/features/cart';

//Publishable Key
const stripePromise = loadStripe('pk_test_51RWwOTPBX86EEJp2xU7s97wzclHrnWZYZDUbW2mdNfNFTNDI2p0eVrqo65iN068tUmVF5WSuLDfzwF6qmugvXTw100Ghredeuo');

const PaymentPage = (props) => {

    const options = {
        mode: 'payment',
        amount: 100,
        currency: 'usd',
        // Fully customizable with appearance API.
        appearance: {
            theme: 'flat'
        },
      };
  return (
    <div>
        <Elements stripe={stripePromise} options={options}>
             <CheckoutForm {...props}/>   
        </Elements>
    </div>
  )
}

export default PaymentPage