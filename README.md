Available requests

# Create product
POST /products
{
    title: String,
    val price: BigDecimal,
    val quantity: Int
}

# Create order
POST /orders
{
    productId: String,
    quantity: Int,
    addressId: String
}

# Get user payment details
GET /users/{id}/payment-details