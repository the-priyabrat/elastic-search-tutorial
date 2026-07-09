# Elasticsearch Search Service

A production-ready Spring Boot microservice demonstrating comprehensive Elasticsearch operations. This project is designed as a learning resource and reference implementation for developers who want to understand how Elasticsearch integrates with Java Spring Boot applications.

## Features

- 🔍 Full Text Search
- 🎯 Keyword Search
- 📝 Multi-field Search
- ⚡ Fuzzy Search
- 🧩 Wildcard Search
- 🔄 Prefix Search
- 🎨 Regex Search
- 📍 Match & Match Phrase Queries
- 🎚️ Boolean Queries (Must, Should, Filter, Must Not)
- 📊 Pagination & Sorting
- 📈 Bulk Indexing
- 💾 Single Document Indexing
- ✏️ Document Update
- ❌ Document Delete
- 📂 Search by ID
- 🏷️ Custom Query DSL Implementation

## Learning Objectives

This repository is intended to help developers learn:

- Spring Boot with Elasticsearch
- Elasticsearch Query DSL
- Search optimization techniques
- Indexing strategies
- Bulk operations
- Clean project architecture
- Exception handling
- REST API design
- Docker-based deployment

## Documentation

Every module is accompanied by:

- Well-structured code
- Inline comments
- Method-level documentation
- Query explanations
- Clean package organization
- Easy-to-follow implementation

The project is designed so developers can understand not only **how** each feature works, but also **why** it is implemented that way.

## Technologies

- Java
- Spring Boot
- Spring Data Elasticsearch
- Elasticsearch
- Docker
- Docker Compose
- Maven

## Running with Docker

Pull the Docker image:

```bash
To be added
```

Run the container:

```bash
docker run -d \
-p 9901:9901 \
--name elasticsearch-search-service \
<your-dockerhub-username>/elasticsearch-search-service:latest
```

> Ensure an Elasticsearch instance is available and update the connection configuration as required.

## API Capabilities

The service provides REST APIs for:

- Create Document
- Bulk Create Documents
- Update Document
- Delete Document
- Get by ID
- Full Text Search
- Fuzzy Search
- Wildcard Search
- Prefix Search
- Regex Search
- Multi-field Search
- Boolean Queries
- Pagination
- Sorting

## Project Goal

This project serves as a practical reference for developers looking to master Elasticsearch with Spring Boot. It combines production-oriented coding practices with comprehensive documentation, making it suitable for learning, experimentation, and integration into larger microservice architectures.

---
⭐ If you find this project useful, consider starring the repository and contributing with improvements or additional search implementations.
