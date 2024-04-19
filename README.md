## Table of Content
1. [EASVoucher Descriptions](#easvoucher)
2. [Functional Requirements](#functional-requirements)
3. [User Interface](#user-interface)
    - [Login Page](#login-page)
    - [Coordinator Panel](#event-coordinator-panel)
    - [Admin Panel](#admin-panel)
    - [Ticket View](#ticket)
4. [Test Credentials](#test-credentials)
    - [Admin](#admin)
    - [Coordinator](#coordinator)


## EASVoucher
Many companies and organisations are organizing events. This is often a complicated task as it involves a lot of management of resources, such as time, places and people.

## Functional Requirements
### Users

There are 3 types of users in the system. Admin and Event Coordinator logs in using a username and password.

Admin, can create and manage all other users. Can delete events and assign coordinators. Cannot create and manage events.

Event Coordinator, can create, delete and manage events and add tickets to events pertaining to that coordinator (set by the admin). Can assign other coordinators to have access.

Customer, end customer. Gets the printed tickets on paper. Does not have access to the system.

### Events

Events can be anything, like a dog show, EASV party, Wine tasting, etc., however all events have some minimum information and optional information:
Required information

**Time**: start date and time

**Location**: Where the event is located, could be a real address, URL, coordinates, etc.

**Notes**: Extra information about the event

Optional information (must be supported but may not be used for all events)

**End date** **and time**

**Location guidance**, how to get to the event e.g. by car

Printed Tickets and admission

The event coordinators must be able to print tickets for the customer. Each ticket must be connected to the name and email of the customer who bought the ticket. One customer can buy multiple tickets. 

The event coordinator either prints the ticket for the customer, or sends the ticket to the customer by email. The ticket can be e.g. a pdf or an image.

The customer buys the ticket directly from the event coordinator IRL. Payment is not part of the system. An event coordinator hands out tickets after being paid.
There are multiple types tickets for participants, customizable. e.g. VIP tickets, Food included tickets, 1st row, free beer, etc. These types/categories of tickets are not fixed but can be customized for each event.

### Tickets must have the following information

**QR code (2d barcode) and corresponding 1d barcode**, this code must resolve to a unique system generated id that cannot be guessed. Avoid using a sequence of numbers or anything else that can be guessed. You might use UUID or similar technology.

**Event information** (see above), both required and optional, except the participant data.
Special free or discounted food/drinks tickets

You must be able to print saperate free tickets for an event, such as "one free beer", "50% off one drink", "1 set of free earplugs", etc. This ticket must contain its own valid QR/barcode for one time use. These tickets are not connected to any specific customers, but can optionally be for a specific event, otherwise it is valid for all events.

e.g. this example of a free beer at a Christmas party


## User Interface 
### Login Page
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/97dbcd22-a78f-402a-a44b-042bd22af1d0)


### Event Coordinator Panel
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/f0d7fcdb-406d-4c86-934e-a99370cb969e)

#### Information about Events and Tickets
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/c002af23-0ccb-477d-9180-165b9470d027)

#### Create new event
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/0fb248bb-766e-4581-91fb-ee7340e5b043)

#### Create new ticket
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/0fe930e9-78b0-456c-a84b-0de874229b2e)

### Admin Panel
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/7aa7495f-6fe1-4acb-bdde-b87ed52908f0)

### Ticket
#### Front
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/068b459a-37a9-4bc1-89d3-763e4c2fd21a)

#### Back
![image](https://github.com/ju5tAmir/EASVoucher/assets/145110623/2c01cede-33e6-4223-8abc-79ce8acd2cf4)


## Test Credentials

### Admin

**Username**: `admin`

**Password**: `admin`

### Coordinator

**Username**: `sam`

**Password**: `sam`



