# Github URL:
https://github.com/kvrmurthysrcm/CRUD-Template-Project.git

# Following are the helpful SQL for populating Products for testing:

SELECT * FROM devschema.products;

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (1, 20, 'Banana', 3, 5);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (2, 25, 'Apple', 2, 4);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (3, 50, 'Grapes Bunch', 4, 6);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (4, 60, 'Pear', 4, 5);
=====================================================================================
# http commands for API CRUD testing
# http [flags] [METHOD] URL [ITEM [ITEM]]

# Get all products:
# Base URL: http://localhost:9000/api/v1/products
# run following command to test
http GET http://localhost:9000/api/v1/products

# Get product by ID:
# URL: http://localhost:9000/api/v1/products/{ID}
# run following command to test
http GET http://localhost:9000/api/v1/products/7

# Delete product by ID:
http://localhost:9000/api/v1/products/{ID}
http DELETE http://localhost:9000/api/v1/products/2

# Create Product:
# Base URL: http POST http://localhost:9000/api/v1/products
# Run the following to test creation of the product...
http POST http://localhost:9000/api/v1/products productName='Cycle' price=20 wight=5 volume=7

# Update Product
http PUT http://localhost:9000/api/v1/products/7 id=7 productName='Cycle' price=25 wight=15 volume=7



