Autors: 
- Mirka Kolarikova (xkolar76), 
- Martin Zovinec(xzovin00)

Contribution: 
- Mirka 90% - almost 7k lines of code
- Martin 10%

## Automated warehouse simulation in java 8.
Simulator skladu zbozi a vydeje objednavek v Java 8.

Je potřeba nejdříve spustit skript lib/get-libs.sh.

<img width="830" alt="image" src="https://user-images.githubusercontent.com/56356131/224340815-6b94e583-8766-4790-bce0-a763058daf81.png">


You can load your own warehouse map file (in YAML format) into the program.

Trolleys fulfill orders one at a time by collecting goods from specific shelves. 
Once they have picked up all the required items, they proceed to a designated pickup point and are ready for the next order.


Each type of good is stored on its own shelf, with a specified weight and number of pieces. 

Trolleys have a weight capacity, and if they cannot complete an entire order due to this limit, they will do so in multiple trips.

Trolleys are intelligent and can determine the most effective path to collect all the goods. 
By clicking on a specific trolley, you can view its current path, capacity, and order progress. Small red dots indicate where the trolley will stop to pick up goods.

<img width="830" alt="image" src="https://user-images.githubusercontent.com/56356131/224342172-de9c0a85-5c6c-4db7-8be4-8f23444baf84.png">


The simulation speed is adjustable.

<img width="269" alt="image" src="https://user-images.githubusercontent.com/56356131/224343093-c91fe2ef-7db8-4688-a771-838484eab70e.png">

You can monitor the progress of all orders in real-time. 

<img width="260" alt="image" src="https://user-images.githubusercontent.com/56356131/224344796-f3011e4e-4e26-4480-ae55-c9dde05870ae.png"><img width="260" alt="image" src="https://user-images.githubusercontent.com/56356131/224345116-6fefd7a7-8ef6-4508-a7ef-d6eb0a91553b.png">

It is also possible to add new orders, although the number of goods in the shelves is limited!

<img width="850" alt="image" src="https://user-images.githubusercontent.com/56356131/224344139-057ed0b6-85f9-4518-b079-718805bd090e.png">

If you need to block a path, you can do so by clicking on it, and it will turn red, indicating that trolleys will not use it and must choose an alternative route.

<img width="576" alt="image" src="https://user-images.githubusercontent.com/56356131/224346775-32419409-ce80-43f0-a882-84f036c5a2d0.png">

The main plane is zoomable.

<img width="810" alt="image" src="https://user-images.githubusercontent.com/56356131/224346942-696acf46-24dc-45a2-83ea-160f56a3d542.png">






