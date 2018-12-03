/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CadastrarFuncionarioController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtSenha;

    private Funcionario funcionario;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void editarFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        txtNome.setText(funcionario.getNome());
        txtUsuario.setText(funcionario.getUsuario());
        txtSenha.setText(funcionario.getSenha());
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome(txtNome.getText());
        funcionario1.setUsuario(txtUsuario.getText());
        funcionario1.setSenha(txtSenha.getText());
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Cadastro de Funcionário realizado com sucesso!");
        alert.showAndWait();
        
        em.getTransaction().begin();
        em.persist(funcionario1);
        em.getTransaction().commit();
        
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.close();
    }
    
}
