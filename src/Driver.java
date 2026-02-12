import bll.EditorBO;
import bll.FacadeBO;
import bll.IFacadeBO;
import dal.AbstractDAOEditorFactory;
import dal.FacadeDAO;
import dal.IEditorDBDAO;
import dal.IFacadeDAO;
import pl.EditorPO;

public class Driver {

	public Driver() {
    }

    public static void main(String[] args) {

    	IEditorDBDAO editorDAO = AbstractDAOEditorFactory.getInstance().createEditorDAO();
        IFacadeDAO facadeDAO = new FacadeDAO(editorDAO);
        IFacadeBO editorBO = new FacadeBO(new EditorBO(facadeDAO));
        new EditorPO(editorBO);
    }
}