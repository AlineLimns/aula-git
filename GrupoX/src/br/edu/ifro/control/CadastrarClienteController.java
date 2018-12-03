/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Cliente;
import br.eti.diegofonseca.MaskFieldUtil;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CadastrarClienteController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtTelefone;
    @FXML
    private RadioButton rbMasculino;
    @FXML
    private RadioButton rbFeminino;
    @FXML
    private Button btnCancelar;

    private Cliente cliente;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.cpfField(txtCpf);
        MaskFieldUtil.foneField(txtTelefone);
    }    

    public void editarCliente(Cliente cliente) {
        this.cliente = cliente;
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtTelefone.setText(cliente.getTelefone());
        if(cliente.getSexo().equals(rbFeminino.getText())) {
            rbFeminino.setSelected(true);
        } else if(cliente.getSexo().equals(rbMasculino.getText())) {
            rbMasculino.setSelected(true);
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Cliente cliente1;
        
        if (cliente != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Edição");
        alert.setContentText("Tem certeza que deseja editar o Cliente selecionada?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Query query = em.createQuery("SELECT a FROM Cliente as a WHERE a.id = :id");
        query.setParameter("id", cliente.getId());
        
        cliente1 = (Cliente) query.getSingleResult();
        cliente1.setNome(txtNome.getText());
        cliente1.setCpf(txtCpf.getText());
        cliente1.setTelefone(txtTelefone.getText());
        if (rbFeminino.isSelected()){
            cliente1.setSexo("Feminino");
        } else {
            cliente1.setSexo("Masculino");
        }
        
        em.getTransaction().begin();
        em.persist(cliente1);
        em.getTransaction().commit();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Edição de Cliente");
        alert2.setHeaderText(null);
        alert2.setContentText("Cliente editado com Sucesso!");

        alert2.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

        } else {
            
        }
        } else { 
    
        cliente1 = new Cliente(); 
        
        cliente1.setNome(txtNome.getText());
        cliente1.setCpf(txtCpf.getText());
        cliente1.setTelefone(txtTelefone.getText());
        if (rbFeminino.isSelected()){
            cliente1.setSexo("Feminino");
        } else {
            cliente1.setSexo("Masculino");
        }
        
        em.getTransaction().begin();
        em.persist(cliente1);
        em.getTransaction().commit();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Cliente");
        alert.setHeaderText(null);
        alert.setContentText("Cliente cadastrado com Sucesso!");

        alert.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        }
    }

    @FXML
    private void limpar(ActionEvent event) {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        rbFeminino.setSelected(false);
        rbMasculino.setSelected(false);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
}
