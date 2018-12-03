/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Prateleira;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CadastrarPrateleiraController implements Initializable {

    @FXML
    private TextField txtNumero;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextArea txtObservacoes;
    @FXML
    private RadioButton rbLivro;
    @FXML
    private ToggleGroup rbTipo;
    @FXML
    private RadioButton rbTese;
    @FXML
    private RadioButton rbReferencia;
    @FXML
    private RadioButton rbPeriodico;

    private Prateleira prateleira;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void editarPrateleira (Prateleira prateleira) {
        this.prateleira = prateleira;
        txtNumero.setText(prateleira.getNumero());
        txtObservacoes.setText(prateleira.getObservacoes());
        if(prateleira.getTipo().equals(rbLivro.getText())){
            rbLivro.setSelected(true);
        } else if(prateleira.getTipo().equals(rbPeriodico.getText())) {
            rbPeriodico.setSelected(true);
        } else if(prateleira.getTipo().equals(rbReferencia.getText())) {
            rbReferencia.setSelected(true);
        } else if(prateleira.getTipo().equals(rbTese.getText())) {
            rbTese.setSelected(true);
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Prateleira prateleira1;
        
        if (prateleira != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Edição");
        alert.setContentText("Tem certeza que deseja editar a prateleira selecionada?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Query query = em.createQuery("SELECT a FROM Prateleira as a WHERE a.id = :id");
        query.setParameter("id", prateleira.getId());
        
        prateleira1 = (Prateleira) query.getSingleResult();
        prateleira1.setNumero(txtNumero.getText());
        prateleira1.setObservacoes(txtObservacoes.getText());
        if (rbLivro.isSelected()){
            prateleira1.setTipo("Livro");
        } else if (rbPeriodico.isSelected()){
            prateleira1.setTipo("Periódico");
        } else if (rbReferencia.isSelected()){
            prateleira1.setTipo("Referência");
        } else if (rbTese.isSelected()){
            prateleira1.setTipo("Tese");
        }
        
        em.getTransaction().begin();
        em.persist(prateleira1);
        em.getTransaction().commit();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Edição de Obras");
        alert2.setHeaderText(null);
        alert2.setContentText("Obra editada com Sucesso!");

        alert2.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

        } else {
            
        }
        } else { 
    
        prateleira1 = new Prateleira(); 
        
        prateleira1.setNumero(txtNumero.getText());
        prateleira1.setObservacoes(txtObservacoes.getText());
        if (rbLivro.isSelected()){
            prateleira1.setTipo("Livro");
        } else if (rbPeriodico.isSelected()){
            prateleira1.setTipo("Periódico");
        } else if (rbReferencia.isSelected()){
            prateleira1.setTipo("Referência");
        } else if (rbTese.isSelected()){
            prateleira1.setTipo("Tese");
        }
        em.getTransaction().begin();
        em.persist(prateleira1);
        em.getTransaction().commit();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Obra");
        alert.setHeaderText(null);
        alert.setContentText("Obra cadastrada com Sucesso!");

        alert.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        limpar();
        }
    }

    @FXML
    private void limpar() {
        rbLivro.setSelected(false);
        rbPeriodico.setSelected(false);
        rbReferencia.setSelected(false);
        rbTese.setSelected(false);
        txtNumero.setText("");
        txtObservacoes.setText("");
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
}
