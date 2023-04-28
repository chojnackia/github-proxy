# GitHub Proxy

This is a simple REST API that acts as a proxy for the GitHub API. It allows you to retrieve the repositories of a given user, excluding forks, along with the branches of each repository.

## Getting started

To build the project, make sure you have Maven installed and run the following command:

`mvn clean install`


The project also requires a GitHub API token, which should be set as an environment variable called `GITHUB_TOKEN`.
(If you don't have one yet) - https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token

Once you have the project built and the environment variable set, you can start the API with the following command:

`docker-compose up --build`


The API will be available at http://localhost:8080. You can access the Swagger documentation at http://localhost:8080/swagger-ui/index.html.

## Endpoints

The API has the following endpoint:

### Get repositories of a user

`GET /api/github/repos/{username}`


Retrieves the repositories of the given GitHub user that are not forks, along with the branches of each repository.

#### Query parameters

None.

#### Path parameters

* `username` - the GitHub username of the user whose repositories to retrieve.

#### Example response

```json
[
  {
    "id": 123,
    "name": "my-repo",
    "owner": {
      "id": 456,
      "login": "my-username"
    },
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "abc123",
          "url": "https://api.github.com/repos/my-username/my-repo/commits/abc123"
        }
      },
      {
        "name": "feature-branch",
        "commit": {
          "sha": "def456",
          "url": "https://api.github.com/repos/my-username/my-repo/commits/def456"
        }
      }
    ]
  },
  {
    "id": 789,
    "name": "my-other-repo",
    "owner": {
      "id": 456,
      "login": "my-username"
    },
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "ghi789",
          "url": "https://api.github.com/repos/my-username/my-other-repo/commits/ghi789"
        }
      }
    ]
  }
]
