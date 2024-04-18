## Proof of Concept: Push Notification System with Quarkus and Vert.x
This proof of concept (POC) demonstrates the implementation of a push notification system using Quarkus and Vert.x. The goal of this project is to design and implement a robust notification system capable of publishing notifications and supporting multiple subscribers.


### Key Features

- <strong>Publish/Subscribe Model</strong>: Enables the system to send notifications to multiple subscribers efficiently.

- <strong>Cluster Compatibility</strong>: Utilizes the Vert.x Event Bus to ensure seamless communication across different nodes in a clustered environment.


### Architecture
The system is built on the Quarkus framework, leveraging the reactive capabilities of Vert.x to handle asynchronous messaging. The use of the Vert.x Event Bus facilitates the distribution of messages across a cluster.