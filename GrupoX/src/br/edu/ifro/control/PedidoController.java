package br.edu.ifro.control;

import br.edu.ifro.model.Pedido;
import br.edu.ifro.model.Produto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class PedidoController implements Initializable {

    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private Button btnAdicionar;
    @FXML
    private TableView<Produto> tbProdutos;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtCliente;
    @FXML
    private Button btnRemover;
    
    private Pedido pedido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT p FROM Produto p ");
        List produtos = query.getResultList();
        
        cbProduto.setItems(FXCollections.observableArrayList(produtos));
        
        pedido = new Pedido();
    }  

    @FXML
    private void adicionarProduto(ActionEvent event) {
        pedido.addProduto(cbProduto.getSelectionModel().getSelectedItem());
        
        tbProdutos.setItems(FXCollections.observableArrayList(pedido.getProdutos()));
    }

    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        pedido.setNomeCliente(txtCliente.getText());
        
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    @FXML
    private void removerProduto(ActionEvent event) {
        pedido.removeProduto(cbProduto.getSelectionModel().getSelectedItem());
        
        tbProdutos.setItems(FXCollections.observableArrayList(pedido.getProdutos()));
    }
    
}
