import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: this class contains {@link by.saidanov.utils.HibernateUtil} tests.
 *
 * @author Artiom Saidanov.
 */
public class HibernateUtilTest {

    @Test
    public void createSessionTest() {
        Session session = HibernateUtil.getHibernateUtil().getSession();
        Assert.assertTrue(session.isOpen());
    }

    @Test
    public void closeSession(){
        Session session = HibernateUtil.getHibernateUtil().getSession();
        HibernateUtil.getHibernateUtil().closeSession();
        Assert.assertFalse(session.isOpen());
    }
}
