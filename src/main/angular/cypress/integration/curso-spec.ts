describe('Navegación en el listado, creación y actualización de cursos', () => {

    let numeroRegistros: number;
  
    it('Visitamos la página principal y navegamos hasta la parte del listado de los cursos', () => {
      cy.visit('/');
      cy.contains('Aprendix');
      cy.get('button#dropdown-cursos').click();
      cy.get('a#curso-listado').click();
  
      cy.url().should('contain', 'curso/list');
  
      cy.get('table tbody tr').should('have.length.at.least', 1);
      cy.get('table tbody tr').contains('Big data');
  
      cy.get('table tbody tr').then($elements => {
        numeroRegistros = $elements.length;
      });
    });
  
      it('Vamos a la zona de creación y damos al botón de guardar. Sale un mensaje de error.', () => {
      cy.get('a#boton-nueva-curso').click();
  
  
      cy.url().should('not.contain', 'list');
  
      cy.get('button#boton-curso-guardar').click();
      cy.get('button#modal-boton-guardar').click();
      cy.get('#mensaje-error').should('exist');
  
  
    });
  
    it('Rellenamos el formularios', () => {
      cy.get('input#curso-nombre').clear().type('Spring con angular');
      cy.get('select#curso-tem').select('Informática');

      cy.get('input#curso-duracion').clear().type('300');
      cy.get('button#boton-curso-guardar').click();
      cy.get('button#modal-boton-guardar').click();
      cy.get('#mensaje-exito').should('exist');
  
  
    });
  
    it('Volvemos a la vista de listado y comprobamos que exista', () => {
      cy.get('button#boton-curso-salir').click();
      cy.get('button#modal-boton-volver').click();
  
      cy.url().should('contain', 'curso/list');
  
      cy.get('table tbody tr').then($elements => {
        cy.get('table tbody tr').should('have.length.at.least', numeroRegistros +1)
      });
  
      cy.get('table tbody tr td').contains('Spring con angular');
    });
  
  
   
  
    it('Da de baja un registro', () => {
        
  
      cy.contains('Spring con angular').first().parent().contains('Baja').click();
      cy.get('button#modal-boton-guardar').click();
      cy.contains('Spring con angular').first().parent().contains('Alta');
    });
  
    it('Vuelve a dar el registro de alta', () => {
  
      cy.contains('Spring con angular').first().parent().contains('Alta').click();
      cy.get('button#modal-boton-guardar').click();
      cy.contains('Spring con angular').first().parent().should('not.contain', 'Alta');
    });


    it('Modifica un registro', () => {
        cy.contains('Spring con angular').first().parent().contains('Modificar').click();
    
        cy.get('input#curso-nombre').clear().type('Spring con jsp');
        cy.get('button#boton-curso-guardar').click();
        cy.get('button#modal-boton-guardar').click();
        cy.get('#mensaje-exito').should('exist');
        cy.get('button#boton-curso-salir').click();
        cy.get('button#modal-boton-volver').click();
        cy.get('table tbody tr td').contains('Spring con jsp');
      });
  })
  