# Abenteuer Kita
(German below)

## Description

Abenteuer Kita is a project that arises from a need to develop better communication in kindergarten environments. With a strong emphasis on user experience and communication, Abenteuer Kita provides a secure and intuitive platform that bridges the gap between parents and teachers.

The application aims to facilitate direct communication between teachers and parents. Currently, nurseries often rely on platforms like WhatsApp to communicate, but this can be cumbersome. Abenteuer Kita addresses these challenges by offering a single sign-on system for data protection, event creation, calendar functionality, and to-go lists for the next day, among other features.
At the moment it is just a project that shows the development in Android Studio using MVVM architecture in Kotlin, Firebase as a database, rest API calls, different fragments, custom XML designs and clean code.

## Key Features

- **Secure User Management:**
  - Only authorized representatives of the Kita have the ability to create and manage users, ensuring controlled and restricted access to the platform. Each user has a unique email address for access, with parents ending in @parents.com and Kita staff in @abenteuer.com, clearly separating the two user groups within the application.

- **Intuitive Interface:**
  - The application offers an easy-to-use interface, with a sidebar containing the information menu, where users can access terms and conditions, privacy policy, open source licenses, and impressum.

- **Manageable Posts:**
  - Teachers can create, edit, and delete posts, allowing for smooth and up-to-date communication about events, activities, and relevant news.

- **Relevant Content:**
  - With five main components, including: Home, Events, Daily, About Us, and Calendar. Aslo have sub screens like Info and ChatIA, Abenteuer Kita provides quick and easy access to essential information for parents and teachers.

- **Security and Privacy:**
  - The application ensures the protection of sensitive data and confidentiality of personal information, offering a direct communication channel that avoids the need for informal means such as WhatsApp groups.

## Technical Aspect

- Abenteuer Kita uses a Firestore database to store users (email and encrypted password), events, useful information for parents and daily information for parents separately and orderly, ensuring efficient and secure data management.

- It uses the Google Maps API to display Kita's precise location, giving users an interactive and hands-on experience.

-  It uses the OpenIA API since it has a chat in which text requests of any type can be made.

## Benefits

- Facilitates communication between parents and teachers.
- Provides secure access to relevant information about the Kita.
- Promotes active participation and collaboration within the educational community.
- Ensures privacy and security of user data.

## Screens

- Welcome Screen

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/5ca08e83-f21c-4ff7-8386-a29e274cdf7c" alt="Wellcome Screen" width="200">


- Login Screen (with animations)


<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/880dab37-b3e0-458e-8e09-4f08485e4ba6" alt="Login 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/f5611de1-3559-47b1-b5d7-6debfd4dbefc" alt="Login 2" width="200">



- Home Screen

  The Home fragment serves as the central hub of Abenteuer Kita, where users are greeted with visually appealing cards representing core values such as art, humanity, music, dance, education, and inclusivity. This clean and intuitive design, with vibrant green menu details, yellow accents, and a white background, creates an inviting atmosphere for users to explore and engage with the app.

  
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/5a1c609a-f412-46ef-8834-54f6b5f35e35" alt="Homescreen 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/d32e5f2b-f755-4ad0-bbb4-cbab02665f0c" alt="Homescreen 2" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/81d2c8f2-5f71-487d-99b6-12d55bcc087a" alt="Homescreen 3" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/f7b9333b-2002-4a1f-8c4f-03cc7f6b3c3b" alt="Homescreen 4" width="200">


  
- Events Screen

In the Event fragment, educators have the ability to create, edit, and delete events, ranging from field trips to parent-teacher conferences. Each event card displays key details such as date, time, location, title, and a collapsible description section. Intuitive navigation options and a clean layout ensure seamless event management for both educators and parents.

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/ca203954-3f79-42b5-9941-29f35527d9e9" alt="events 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/e1edbc62-dab2-4ca1-a14c-62d0e4665f3d" alt="events 2" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/834383dd-531a-49ba-855c-052694f4f06d" alt="events 3" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/cd25a9dd-1c2f-47a3-8bee-1277086db30a" alt="events 4" width="200">



- Daily Screen

The Daily fragment features two distinct sections: a “List of Needs” and “Important Information for Parents.” The List of Needs allows staff members to communicate upcoming requirements for children, while the Important Information cards serve as a platform for sharing vital updates and resources. This clean and organized layout promotes easy access to essential information, enhancing parental engagement and involvement.

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/92fc3563-19d9-4c67-abfe-a9445c0cda11" alt="daily 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/574d4f5c-519e-4e9d-8516-0c27945851c9" alt="daily 2" width="200">


- About Us Screen

In the About Us fragment, users can access a map displaying the Kita’s location and contact details. Additionally, a contact form enables direct communication with the Kita via email, ensuring that parents and caregivers can easily reach out with questions or concerns. The clean and intuitive design of this fragment fosters seamless interaction and connection with the Kita community.


<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/c919d2fb-f8b4-4a18-96cb-3ab29f65b4c1" alt="AboutUs 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/c5c71960-40fc-4595-ae0c-61121b02eeb7" alt="AboutUs 2" width="200">


- Calendar Screen

The Calendar fragment offers a comprehensive view of upcoming events and activities, allowing users to navigate seamlessly through the month and access detailed event information with a single tap. A convenient checkbox enables users to filter events based on their preferences, ensuring a personalized and efficient scheduling experience. This clean and visually appealing layout promotes easy navigation and enhances user satisfaction.

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/b5bef904-d70a-4711-9199-2d88670c34a9" alt="Calendar 3" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/7d5022f6-07d9-4c6a-994b-ba57e496739b" alt="Calendar 1" width="200">


- Chat AI Screen

  Abenteuer Kita integrates an AI-powered bot using the OpenAI API, allowing users to ask open-ended questions and receive informative responses. Accessible from both the Home screen and the sidebar of the application, the bot provides a convenient and efficient way for users to seek answers to general inquiries, further enhancing the user experience and promoting engagement with the app.
  
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/9700644d-d097-425b-9f08-b8a4cde437cf" alt="ChatIa 2" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/8851bbfc-e90d-4692-ac90-efe0d675e674" alt="ChatIa 1" width="200">

- Info Window

  The Info window displays the current version of the app and features a menu with four options: Terms and Conditions, Data Protection, Open Source License, and Impressum. Each option provides necessary legal information about the app, albeit fictitious and solely for educational purposes.

By presenting these screenshots, viewers can gain a better understanding of Abenteuer Kita’s features and functionalities, ultimately enhancing their perception of the app and its capabilities.

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/843e22ce-91d5-4368-b7f1-a2ab815c9ee5" alt="Info 2" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/4e1c3933-4254-4254-97ed-0cab58334ae1" alt="Info 1" width="200">


- Sidebar Menu

The sidebar navigation menu provides quick access to key features, including Home, Bot IA, New User (for account creation), Log Out, and Info (for accessing terms, data protection policies, licenses, and impressum). This intuitive navigation system ensures that users can easily access the features and information they need, enhancing overall usability and user satisfaction.

<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/e70cd394-21da-44b9-a0c9-9d35473dd9ca" alt="sidebar 1" width="200">
<img src="https://github.com/SI-Classroom-Batch-013/android-abschluss-Pablo-Mdz/assets/80485682/c0bf689e-e235-4032-b25f-c28ec5b8bad3" alt="sidebar 2" width="200">


## Usage

Abenteuer Kita is more than just an application; it is a fundamental tool for strengthening the connection between the Kita, parents, and teachers, fostering a collaborative and safe educational environment for the development and well-being of children. You can clone the repository and use it for educational purposes.

## Conclusion
Abenteuer Kita is more than just an application; it is a fundamental tool for strengthening the connection between the Kita, parents, and teachers. By leveraging technology to streamline communication, enhance collaboration, and provide access to valuable resources such as the AI-powered bot, Abenteuer Kita is revolutionizing early childhood education and empowering stakeholders to create a brighter future for our children.

To see all my projects, visit www.pablocigoy.com


## German
# Abenteuer Kita

## Beschreibung

Abenteuer Kita ist ein Projekt, das aus dem Bedürfnis heraus entsteht, eine bessere Kommunikation im Kindergartenumfeld zu entwickeln. Mit einem starken Fokus auf Benutzererfahrung und Kommunikation bietet Abenteuer Kita eine sichere und intuitive Plattform, die die Lücke zwischen Eltern und Lehrern schließt.

Die Anwendung soll die direkte Kommunikation zwischen Lehrern und Eltern erleichtern. Derzeit sind Kindergärten für die Kommunikation häufig auf Plattformen wie WhatsApp angewiesen, was jedoch umständlich sein kann. Abenteuer Kita begegnet diesen Herausforderungen, indem es unter anderem ein Single-Sign-On-System für Datenschutz, Veranstaltungserstellung, Kalenderfunktionalität und To-Go-Listen für den nächsten Tag anbietet.
Im Moment handelt es sich nur um ein Projekt, das die Entwicklung in Android Studio unter Verwendung der MVVM-Architektur in Kotlin, Firebase als Datenbank, Rest-API-Aufrufen, verschiedenen Fragmenten, benutzerdefinierten XML-Designs und sauberem Code zeigt.

## Hauptmerkmale

- **Sicheres Benutzermanagement:**
  - Nur autorisierte Vertreter der Kita haben die Möglichkeit, Benutzer zu erstellen und zu verwalten, was einen kontrollierten und eingeschränkten Zugriff auf die Plattform gewährleistet. Jeder Benutzer hat eine eindeutige E-Mail-Adresse für den Zugriff, wobei Eltern mit @parents.com und Kita-Mitarbeiter mit @abenteuer.com enden und die beiden Benutzergruppen innerhalb der Anwendung klar voneinander trennen.

- **Intuitive Benutzeroberfläche:**
  - Die Anwendung bietet eine benutzerfreundliche Oberfläche mit einer Seitenleiste, die das Informationsmenü enthält, über das Benutzer auf Nutzungsbedingungen, Datenschutzrichtlinien, Open-Source-Lizenzen und das Impressum zugreifen können.

- **Verwaltbare Beiträge:**
  - Lehrer können Beiträge erstellen, bearbeiten und löschen, um eine reibungslose und aktuelle Kommunikation über Ereignisse, Aktivitäten und relevante Nachrichten zu ermöglichen.

- **Relevante Inhalte:**
  - Mit fünf Hauptkomponenten, darunter: Startseite, Ereignisse, Täglich, Über uns und Kalender. Neben Unterbildschirmen wie Info und ChatIA bietet Abenteuer Kita Eltern und Lehrern schnellen und einfachen Zugriff auf wichtige Informationen.

- **Sicherheit und Datenschutz:**
  - Die Anwendung gewährleistet den Schutz sensibler Daten und die Vertraulichkeit persönlicher Informationen, indem sie einen direkten Kommunikationskanal anbietet, der den Bedarf an informellen Mitteln wie WhatsApp-Gruppen vermeidet.

## Technischer Aspekt

- Abenteuer Kita nutzt eine Firestore-Datenbank, um Benutzer (E-Mail und verschlüsseltes Passwort), Veranstaltungen, nützliche Informationen für Eltern und Tagesinformationen für Eltern getrennt und geordnet zu speichern und so eine effiziente und sichere Datenverwaltung zu gewährleisten.

- Es nutzt die Google Maps API, um den genauen Standort von Kita anzuzeigen und den Nutzern ein interaktives und praktisches Erlebnis zu bieten.

- Es nutzt die OpenIA-API, da es über einen Chat verfügt, in dem Textanfragen jeglicher Art gestellt werden können.

## Vorteile

- Erleichtert die Kommunikation zwischen Eltern und Lehrern.
- Bietet einen sicheren Zugriff auf relevante Informationen über die Kita.
- Fördert aktive Beteiligung und Zusammenarbeit innerhalb der Bildungsgemeinschaft.
- Gewährleistet Datenschutz und -sicherheit für Benutzerdaten.

## Screens

siehe Bilder in der Beschreibung auf Englisch.

## Verwendung

Abenteuer Kita ist mehr als nur eine Anwendung; sie ist ein grundlegendes Werkzeug zur Stärkung der Verbindung zwischen der Kita, Eltern und Lehrern und fördert eine kooperative und sichere Lernumgebung für die Entwicklung und das Wohlergehen von Kindern. Sie können das Repository klonen und für pädagogische Zwecke verwenden.


## Abschluss
Abenteuer Kita ist mehr als nur eine Anwendung; Es ist ein grundlegendes Instrument zur Stärkung der Verbindung zwischen Kita, Eltern und Lehrern. Durch den Einsatz von Technologie, um die Kommunikation zu optimieren, die Zusammenarbeit zu verbessern und Zugang zu wertvollen Ressourcen wie dem KI-gestützten Bot zu ermöglichen, revolutioniert Abenteuer Kita die frühkindliche Bildung und befähigt Interessenvertreter, eine bessere Zukunft für unsere Kinder zu schaffen.

Um alle meine Projekte zu sehen, besuchen Sie www.pablocigoy.com
