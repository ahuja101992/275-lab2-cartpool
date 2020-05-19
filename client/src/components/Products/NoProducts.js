import React from "react";

const NoProducts = () => {
    return (
        <div className="products">
            <div className="no-results">
                <img
                    src="https://res.cloudinary.com/sivadass/image/upload/v1494699523/icons/bare-tree.png"
                    alt="Empty Tree"
                />
                <h2>Sorry, you have not added any products. !</h2>
                <p>Please add products.</p>
            </div>
        </div>
    );
};

export default NoProducts;
