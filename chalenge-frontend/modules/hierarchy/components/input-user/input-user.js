angular.module('app.hierarchy')
  .component('appInputUser', {
    templateUrl: './modules/hierarchy/components/input-user/input-user.html',
    controller: InputUserController
  });

function InputUserController(HierarchyFacade, $scope, $document) {

  var $ctrl = this;

  $scope.$watch(() => {
    return HierarchyFacade.superiors;
  }, (superiors) => {
    $ctrl.superiors = []
    if (superiors.length > 0) {
      $ctrl.superiors = superiors;
    }
  }, true);

  $ctrl.selectSuperior = superior => {
    $ctrl.superiorSearch = superior.name
    $ctrl.superior = superior
    $ctrl.superiors = []
  };

  $ctrl.searchSuperiors = inputText => {
    $ctrl.superior = undefined
    HierarchyFacade.getSuperiorsByName(inputText);
  };

  $ctrl.submit = () => {
    var userData = {
      name: $ctrl.name,
      password: $ctrl.password,
      idSuperior: $ctrl.isSubordinate ? $ctrl.superior.id : undefined
    };
    HierarchyFacade.createUser(userData);

    $ctrl.name = '';
    $ctrl.password = '';
  };

  $ctrl.submitDisabled = () => {
    if ($ctrl.isSubordinate) {
      return !$ctrl.superior || !$ctrl.name || !$ctrl.password;
    }
    return !$ctrl.name || !$ctrl.password;
  };

  $document.on('click', (event) => {
    var dropdown = angular.element(document.querySelector('.dropdown-menu'));
    var input = angular.element(document.querySelector('#superiorInput'));

    if (!dropdown[0].contains(event.target) && !input[0].contains(event.target)) {
      $scope.$apply($ctrl.showDropdown = false);
    } else {
      $scope.$apply($ctrl.showDropdown = true);
    }
  });

}
