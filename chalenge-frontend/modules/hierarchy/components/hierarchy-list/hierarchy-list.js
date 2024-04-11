angular.module('app.hierarchy')
  .component('appHierarchyList', {
    templateUrl: './modules/hierarchy/components/hierarchy-list/hierarchy-list.html',
    controller: HierarchyListController
  });

function HierarchyListController(HierarchyFacade,  $scope) {
  $ctrl = this;
  
  $ctrl.$onInit = () => {
    HierarchyFacade.getSuperiors()
  }

  $scope.$watch(() => {
    return HierarchyFacade.hierarchyUsers;
  }, (hierarchyUsers) => {
      $ctrl.hierarchyUsers = hierarchyUsers;
  }, true);
}
