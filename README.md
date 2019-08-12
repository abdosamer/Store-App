# Store-App
this is a project based on MVVM with sqllite "android Architecture Components" built for stores to put them products inside it 
with material design principles

## Project client description

the app will help you improve the local business that you can add all the products in the store and add the quantity of it


### Product : 
- name  "the name of the product inside the App".
- price "the product price for the customers".
- code  "the product code: its a placeHolder for the barcode upc_a that will replace it in the next version ".
- quantity "the quantity of the product that you have in the store".
- quantity limit " the minimum quantity of the product that the app will give you a warning if you reach it".

you can sell a product when you swipe it to left or right  each swipe will reduce the quantity number and when you click the product 
you can change its values price, quantity, etc any change in the quantity will be recorded and add to a page called logs that you
can reach it from the drop-down menu 

for example, if you reduce the quantity of the product while editing the app will record it in the logs as a loss but if you sell a 
product by swiping it will be recorded in logs as a profit

### logs :
- name " here will type the name of the product + plus the quantity of it"
- value "the amount of money that you earned or lost "
- type "indicates if its a loss or a profit"

the last section is bills that you can create a new bill from it and add the current products that you are selling in it
and get the total price of the bill and the billing name is the date of it in UNIX time 

### bills 
- date
- total price
#### bill item 
- name
- price
- quantity
- total price
## Extra Project Developer discreption

project sql tables is three

### products table
- _id @PrimaryKey
- name  "the name of the product inside the App".
- price "the product price for the customers".   
- code  "the product code: its a placeHolder for the barcode upc_a that will replace it in the next version ".
- quantity "the quantity of the product that you have in the store".
- quantity limit " the minimum quantity of the product that the app will give you a warning if you reach it".

### logs table
- _ID @primaryKey
- name " here will type the name of the product + plus the quantity of it"
- value "the amount of money that you earned or lost "
- type "indicates if its a loss or a profit"

### bills table
- _BID
- date
- total price
#### bill item table
- _ID
- _BID " the associated bill id "
- name
- price
- quantity
- total price

##### this is the alpha version the next version I will add new futures

- print bill with a thermal printer
- add barcodes to products
- sell-by barcode



