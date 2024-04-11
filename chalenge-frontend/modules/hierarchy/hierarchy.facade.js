angular.module('app.hierarchy', [])
  .service('HierarchyFacade', HierarchyFacade);

function HierarchyFacade(HierarchyApi) {

  var self = this;

  self.hierarchyUsers = [];
  self.superiors = [];

  self.getSuperiorsByName = name => {
    HierarchyApi.getSuperiorsByName(name)
      .then((response) => {
        self.superiors = response
      }) 
      .catch((error) => {
        console.error('Erro ao obter superiores pelo nome:', error);
      });
  }

  self.getSuperiors = () => {
    HierarchyApi.getSuperiors()
      .then((response) => {
        self.hierarchyUsers = response
      })
      .catch((error) => {
        console.error('Erro ao obter superiores pelo nome:', error);
      });
  }
  
  self.createUser = user =>{
    HierarchyApi.createUser(user)
      .then(() => {
        self.getSuperiors()
      }) 
      .catch((error) => {
        console.error('Erro ao salvar usuário:', error);
      });
  }

  self.getSubordinates = user =>{
    HierarchyApi.getSubordinates(user.id)
      .then((response) => {
        user.subordinates = response
        console.log(user.subordinates)
      }) 
      .catch((error) => {
        console.error('Erro ao salvar usuário:', error);
      });
  }
}

