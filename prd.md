# Project: Policy Reminder
### Insurance Agent CRM & Policy Management Application

## 1. Project Overview

Policy Reminder is a mobile application designed specifically for insurance agents and advisors to simplify customer relationship management, insurance policy tracking, renewal management and customer communication.

Insurance agents typically manage multiple customers, each having one or more insurance policies with different renewal dates. They also need to maintain customer relationships by remembering birthdays, wedding anniversaries and other important occasions.

Managing these details manually through diaries, spreadsheets, phone contacts or separate reminder applications can become difficult and lead to missed renewals and forgotten follow-ups.

Policy Reminder aims to centralize all these activities into one simple, organized and easy-to-use mobile application.

The application acts as a personal digital assistant for insurance agents, helping them stay organized, maintain regular contact with customers and ensure timely policy renewals.

## 2. Target Users

The primary target users are:
- Independent insurance agents.
- Insurance advisors.
- Individual insurance brokers.
- Small insurance agencies.

The application is primarily intended for agents who independently manage a portfolio of customers and their insurance policies.

The interface should prioritize simplicity, quick access to information and minimal steps for completing everyday tasks.

## 3. Core Objectives

The application has six primary objectives:

1. Maintain a centralized database of customers and their family information.
2. Store and manage multiple insurance policies for individual customers.
3. Automatically generate reminders for upcoming policy renewal dates.
4. Track birthdays and anniversaries of customers and their family members.
5. Facilitate personalized customer communication through WhatsApp.
6. Help agents manage client calls, follow-ups and renewal history.

## 4. Core Functionalities

### A. Customer Management

The application should allow agents to add, edit, search, view and manage customer records.

Customer information includes:
- First name.
- Middle name.
- Surname.
- Mobile number.
- Alternate contact number.
- Email address.
- Residential address.
- Date of birth.

Agents should also be able to add family members associated with a customer.

Family information should support relationships such as:
- Father.
- Mother.
- Spouse.
- Children.
- Other family members.

Each family member can have their own name, date of birth and relevant anniversary information.

Family member records should be dynamically addable rather than restricted to a fixed number.

A customer profile should display personal details, family information, associated insurance policies, upcoming reminders and communication history.

### B. Insurance Policy Management

An individual customer may have multiple insurance policies.

The application should allow agents to create and manage separate policy records associated with a customer.

Policy information includes:
- Policy number.
- Insurance company.
- Policy type.
- Policyholder.
- Policy start date.
- Renewal or due date.
- Premium amount.
- Payment frequency.
- Current policy status.

Supported policy categories may include life insurance, health insurance, motor insurance and other insurance products.

Policy statuses should include Active, Due Soon, Overdue, Renewed and Lapsed, where applicable.

### C. Policy Renewal Reminder System

This is one of the application's most important functionalities.

The application should monitor upcoming policy renewal dates and generate reminders automatically.

Agents should be able to customize reminder intervals.

For example:
- 30 days before renewal.
- 15 days before renewal.
- 7 days before renewal.
- 1 day before renewal.
- On the renewal date.

These intervals should not be hardcoded.

The application should support:
- Global default reminder settings.
- Individual policy-level reminder customization.
- Enabling or disabling reminder stages.
- Configurable notification times.
- Snoozing and completing reminders.

Each policy should have an independent reminder schedule.

### D. Birthday and Anniversary Management

The application should track birthdays and wedding anniversaries of customers and their family members.

Agents should receive notifications about upcoming occasions.

The application should support customizable reminder timing and personalized message templates.

For example, when a customer's birthday arrives, the agent should be able to open the reminder, preview a personalized message and send it through WhatsApp.

### E. WhatsApp Communication

WhatsApp is the preferred communication channel.

The application should provide customizable message templates for:
- Birthday wishes.
- Anniversary wishes.
- Policy renewal notifications.
- Customer follow-ups.

Templates should support dynamic placeholders such as customer name, agent name, policy number and renewal date.

The initial implementation should use WhatsApp Click-to-Chat, which opens WhatsApp with a prefilled message for the agent to review and send manually.

The application should not claim that a message has been delivered merely because WhatsApp was opened.

### F. Client Call Management

Personal phone calls are preferred for policy renewal follow-ups.

Each policy reminder should provide a quick option to call the associated customer.

Agents should be able to record:
- Date of the call.
- Call outcome.
- Customer response.
- Follow-up date.
- Additional remarks.

Possible call outcomes include:
- Will renew.
- Interested.
- No response.
- Call later.
- Not interested.
- Already renewed.

The application should maintain a history of customer interactions.

### G. Policy Renewal Updates

Policy renewal information must initially be updated manually by the agent.

When a policy is renewed, the agent should be able to update:
- New renewal or expiry date.
- Updated premium amount.
- Payment frequency, if changed.
- Renewal remarks.

The application should preserve previous renewal records rather than simply overwriting them.

After updating the policy:
1. Previous renewal reminders should be invalidated.
2. The new policy details should be saved.
3. A renewal history entry should be created.
4. A fresh reminder schedule should be generated using the updated due date.

## 5. Homepage Purpose

The homepage should function as the agent's daily operational dashboard.

It should provide an immediate overview of important activities and allow quick access to the most frequently used functionalities.

The homepage should prioritize:

- Adding customers.
- Adding insurance policies.
- Accessing policy renewals.
- Viewing upcoming birthdays.
- Viewing upcoming anniversaries.
- Managing follow-up calls.
- Viewing existing policies.
- Checking upcoming reminders.

The dashboard should focus on actionable information rather than displaying excessive statistics or decorative elements.

## 6. Application Navigation

The application should have five primary bottom navigation sections:

1. Home – Dashboard and daily activities.
2. Customers – Customer database and family information.
3. Policies – Insurance policy records and renewal details.
4. Reminders – Upcoming birthdays, anniversaries, renewals and follow-ups.
5. Settings – Agent profile, reminder preferences and message templates.

## 7. Design Philosophy

The application should have a minimal, sophisticated and premium visual identity.

The design must follow a strict two-colour system.

Primary background:
#222223 (Charcoal Grey)

Primary foreground:
#E7E5D7 (Warm Off-White)

No additional accent colours should be introduced.

Design characteristics:
- Dark charcoal backgrounds.
- Warm off-white cards and typography.
- Rounded rectangular surfaces.
- Subtle dotted textures.
- Minimal outline icons.
- Clean typography.
- Balanced spacing.
- Clear visual hierarchy.
- Compact information cards.
- Simple and functional navigation.

The interface should avoid unnecessary decorative elements, colourful gradients, excessive badges, flags, random illustrations and visual clutter.

The overall experience should feel like a premium consumer mobile application rather than a conventional enterprise CRM dashboard.

The supplied visual reference screenshots should guide the layout, proportions, card arrangements, spacing and overall composition, while the specified monochromatic colour system takes priority.

## 8. Technical and Functional Expectations

The initial product is intended to be an Android mobile application.

Recommended implementation:
- Kotlin and Jetpack Compose for the mobile interface.
- MVVM architecture.
- Room database for local data persistence.
- WorkManager for background reminder scheduling.
- Android notifications for local alerts.
- WhatsApp Click-to-Chat for customer communication.

The application should follow an offline-first approach.

Customer records, policy information and reminder settings should remain accessible without an internet connection.

Internet connectivity will be required for WhatsApp communication and optional cloud backup.

## 9. Product Scope

The first release should focus on the essential functionalities:

- Customer registration and management.
- Family member information.
- Multiple policies per customer.
- Configurable renewal reminders.
- Birthday and anniversary reminders.
- WhatsApp message templates and communication.
- Customer calling and follow-up tracking.
- Manual renewal updates.
- Renewal history.

Advanced functionalities such as automated WhatsApp messaging, cloud synchronization, multi-agent support and advanced analytics can be introduced in future versions.

## 10. Overall Product Vision

Policy Reminder should become a reliable everyday companion for insurance agents.

Its purpose is not simply to notify agents about upcoming dates. It should help them manage their entire customer relationship lifecycle, from maintaining customer information and tracking policies to communicating with customers and ensuring timely renewals.

Every screen and interaction should be designed around one central principle:

**Help insurance agents manage their customers, remember important dates and never miss a policy renewal — with minimum effort.**