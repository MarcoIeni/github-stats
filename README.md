# GitHub Stats
GitHub stats use the [GitHub API v4](https://developer.github.com/v4/) that uses GraphQL.

Oppure:
GitHub stats use web scraping instead of GitHub API because in this way you don't have to login.
Furthermore at time of writing GitHub API v4 does not support all the stats provided by this application.

## queries

```
{
  rateLimit{
    cost
    remaining
  }
  repository(owner: "syl20bnr", name: "spacemacs") {
    nameWithOwner
    forkCount
    stargazers {
      totalCount
    }
    pullRequests: pullRequests {
      totalCount
    }
    watchers {
      totalCount
    }
    openPullRequests: pullRequests(states:[OPEN]) {
      totalCount
    }
    issues {
      totalCount
    }
    openIssues: issues(states:[OPEN]) {
      totalCount
    }
    commits: object(expression:"master") {
      ... on Commit {
        history {
          totalCount
        }
      }
    }
  }
}
```

## Build and execute
To execute the cli app execute the gradle: tasks/distribution/installDist
Then execute the script in build/install/github-stats/bin

## Example
```
ghs login <username> <password>

ghs logout

# show tracked stats
ghs show

# add MarcoIeni to tracked users
ghs add MarcoIeni

# add MarcoIeni/intelli-space to tracked repos
ghs add MarcoIeni/intelli-space

# show tracked users and repos
ghs ls

# remove repo or user from tracked elements
ghs rm MarcoIeni/intelli-space

# set cache expiry time to 30 minutes
ghs expire-time 30
```
