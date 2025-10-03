# GraphqlDataloader
The DataLoader is a very handy pattern to solve the N+1 problem, which arises when a query result contains a field that has to be queried N times.

#Problem Statement
You're building a simple inventory system for an e-commerce platform. Create a GraphQL API that:
Product Entity: Contains id, name, price, and stock quantity
GraphQL Query: Fetch product by ID and check if it's available (stock > 0)
GraphQL Mutation: Update product stock with validation
DataLoader: Batch load multiple products efficiently
Requirements:
Query: product(id: String!): Product
Mutation: updateStock(id: String!, quantity: Int!): Product
Use DataLoader to prevent N+1 queries when loading multiple products
Validate: quantity must be non-negative, product must exist
