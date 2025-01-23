rem Create a User
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"username\": \"john_doe\", \"email\": \"john@example.com\"}"
rem Create a Task
curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d "{\"title\": \"Sample Task\", \"description\": \"A task description\", \"status\": \"pending\", \"dueDate\": \"2024-12-31\"}"
rem Create a Subtask Associated with the Task
curl -X POST http://localhost:8080/subtasks -H "Content-Type: application/json" -d "{\"title\": \"Subtask 1\", \"status\": \"IN_PROGRESS\", \"taskId\": 1}"
rem Associate the Task with the User
curl -X POST http://localhost:8080/tasks/1/users/1
rem Display the User, Associated Task, and Subtask
curl -X GET http://localhost:8080/users/1
