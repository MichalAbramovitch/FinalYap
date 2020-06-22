import React from 'react';
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css"
import Loader from 'react-loader-spinner'
import "./LoadingSpinner.css";

const LoadingSpinner = () => (
  <div>
	File is been processing
	<Loader className="loader" type="ThreeDots" color="#00BFFF" height={60} width={60}  />
  </div>
);

export default LoadingSpinner;