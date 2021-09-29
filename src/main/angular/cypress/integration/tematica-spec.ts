describe('Navegación en el listado, creación y actualización de temáticas', () => {

  let numeroRegistros: number;

  it('Visitamos la página principal y navegamos hasta la parte del listado de las temáticas', () => {
    cy.visit('/');
    cy.contains('Aprendix');
    cy.get('button#dropdown-tematica').click();
    cy.get('a#tematica-listado').click();

    cy.url().should('contain', 'tematica/list');

    cy.get('table tbody tr').should('have.length.at.least', 1);
    cy.get('table tbody tr').contains('Informática');

    cy.get('table tbody tr').then($elements => {
      numeroRegistros = $elements.length;
    });
  });

    it('Vamos a la zona de creación y damos al botón de guardar. Sale un mensaje de error.', () => {
    cy.get('a#boton-nueva-tematica').click();


    cy.url().should('not.contain', 'list');

    cy.get('button#boton-tematica-guardar').click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('#mensaje-error').should('exist');


  });

  it('Rellenamos el formularios', () => {
    cy.get('input#tematica-nombre').clear().type('Ejercicios físicos');
    cy.get('input#tematica-ref').clear().type('SAL-EJER-FIS');
    cy.get('select#tematica-cat').select('Ciencias');
    cy.get('table.table.subtematica tbody tr').should('have.length', 0);
    cy.get('input#tematica-sub').clear().type('Cardio');
    cy.get('button#tematica-sub-boton').click();
    cy.get('input#tematica-sub').clear().type('Pilates');
    cy.get('button#tematica-sub-boton').click();
    cy.get('table.table.subtematica tbody tr').should('have.length', 2);
    cy.get('button#boton-eliminar-subtematica').first().click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('table.table.subtematica tbody tr').should('have.length', 1);
    cy.get('button#boton-tematica-guardar').click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('#mensaje-exito').should('exist');


  });

  it('Volvemos a la vista de listado y comprobamos que exista', () => {
    cy.get('button#boton-tematica-salir').click();
    cy.get('button#modal-boton-volver').click();

    cy.url().should('contain', 'tematica/list');

    cy.get('table tbody tr').then($elements => {
      cy.get('table tbody tr').should('have.length.at.least', numeroRegistros +1)
    });

    cy.get('table tbody tr td').contains('Ejercicios físicos');

    cy.contains('Ejercicios físicos').first().parent().contains('Borrar').click();
    cy.get('button#modal-boton-guardar').click();

    cy.get('table tbody tr').then($elements => {
      cy.get('table tbody tr').should('have.length.at.least', numeroRegistros)
    });
  });


  it('Creamos otro registro', () => { 
    cy.get('a#boton-nueva-tematica').click();
    cy.url().should('not.contain', 'list');
    cy.get('input#tematica-nombre').clear().type('Ejercicios físicos');
    cy.get('input#tematica-ref').clear().type('SAL-EJER-FIS');
    cy.get('select#tematica-cat').select('Ciencias');
    cy.get('table.table.subtematica tbody tr').should('have.length', 0);
    cy.get('input#tematica-sub').clear().type('Cardio');
    cy.get('button#tematica-sub-boton').click();
    cy.get('input#tematica-sub').clear().type('Pilates');
    cy.get('button#tematica-sub-boton').click();
    cy.get('table.table.subtematica tbody tr').should('have.length', 2);
    cy.get('button#boton-eliminar-subtematica').first().click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('table.table.subtematica tbody tr').should('have.length', 1);
    cy.get('button#boton-tematica-guardar').click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('#mensaje-exito').should('exist');
    cy.get('button#boton-tematica-salir').click();
    cy.get('button#modal-boton-volver').click();

  });

  it('Comprueba que haya un nuevo registro y lo da de baja', () => {
    cy.get('table tbody tr').then($elements => {
      cy.get('table tbody tr').should('have.length.at.least', numeroRegistros+1)
    });

    cy.contains('Ejercicios físicos').first().parent().contains('Baja').click();
    cy.get('button#modal-boton-guardar').click();
    cy.contains('Ejercicios físicos').first().parent().contains('Alta');
  });

  it('Vuelve a dar el registro de alta', () => {
    cy.get('table tbody tr').then($elements => {
      cy.get('table tbody tr').should('have.length.at.least', numeroRegistros+1)
    });

    cy.contains('Ejercicios físicos').first().parent().contains('Alta').click();
    cy.get('button#modal-boton-guardar').click();
    cy.contains('Ejercicios físicos').first().parent().should('not.contain', 'Alta');
  });

  it('Modifica un registro', () => {
    cy.contains('Ejercicios físicos').first().parent().contains('Modificar').click();

    cy.get('input#tematica-nombre').clear().type('Ejercicios físicos modificados');
    cy.get('button#boton-tematica-guardar').click();
    cy.get('button#modal-boton-guardar').click();
    cy.get('#mensaje-exito').should('exist');
    cy.get('button#boton-tematica-salir').click();
    cy.get('button#modal-boton-volver').click();
    cy.get('table tbody tr td').contains('Ejercicios físicos modificados');
  });
})
