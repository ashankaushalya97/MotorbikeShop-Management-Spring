package lk.ijse.dep.rcrmoto.dao.custom.impl;

import lk.ijse.dep.rcrmoto.dao.custom.QueryDAO;
import lk.ijse.dep.rcrmoto.entity.CustomEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public List<CustomEntity> getOrderInfo() throws Exception {
        return session.createNativeQuery("SELECT orders.orderId,orders.date,customer.customerId,customer.name,sum((orderDetail.qty)*(orderDetail.unitPrice)) as total FROM ((orders INNER JOIN orderDetail ON orders.orderId = orderDetail.orderId) INNER JOIN customer ON orders.customerId = customer.customerId) group by orders.orderId")
                .list();
    }

    @Override
    public List<CustomEntity> searchOrder(String text) throws Exception {
        return session.createNativeQuery("SELECT orders.orderId,orders.date,customer.customerId,customer.name,sum((orderDetail.qty)*(orderDetail.unitPrice)) FROM ((orders INNER JOIN orderDetail ON orders.orderId = orderDetail.orderId) INNER JOIN customer ON orders.customerId = customer.customerId) group by orders.orderId having orders.orderId LIKE ?1 OR orders.date LIKE ?2 OR customer.customerId LIKE ?3 OR customer.name LIKE ?4 OR sum((orderDetail.qty)*(orderDetail.unitPrice)) LIKE ?5")
            .setParameter(1,text).setParameter(2,text).setParameter(3,text).setParameter(4,text).setParameter(5,text).list();
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
