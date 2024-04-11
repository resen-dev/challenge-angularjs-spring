angular.module('app.hierarchy')
  .service('HierarchyApi', HierarchyApi);

function HierarchyApi($http) {
  
  var self = this;

  var apiUrl = 'http://localhost:8080/api/users/v1';

  self.getUser = id => {
    return $http.get(apiUrl + '/' + id)
      .then(function (response) {
        return response.data;
      })
  }

  self.createUser = user => {
    return $http.post(apiUrl, user)
      .then(function (response) {
        return response.data;
      })
  }

  self.getSuperiorsByName = name => {
    return $http.get(apiUrl + '/superiors-by-name', { params: { name: name } })
      .then(function (response) {
        return response.data;
      })
  }

  self.getSuperiors = () => {
    return $http.get(apiUrl + '/superiors')
    .then(function (response) {
      return response.data;
    })
  }

  self.getSubordinates = (idUser) => {
    return $http.get(apiUrl + '/subordinates/'+idUser)
    .then(function (response) {
      return response.data;
    })
  }
}
