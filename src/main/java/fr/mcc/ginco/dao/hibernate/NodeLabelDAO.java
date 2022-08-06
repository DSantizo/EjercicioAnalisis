/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.NodeLabel;
import fr.mcc.ginco.dao.INodeLabelDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class NodeLabelDAO extends GenericHibernateDAO<NodeLabel, Integer>
		implements INodeLabelDAO {

	public NodeLabelDAO() {
		super(NodeLabel.class);
	}

	@Override
	public NodeLabel getByThesaurusArray(String thesaurusArrayId) {
		Criteria criteria = getCurrentSession().createCriteria(
				NodeLabel.class);
		criteria.add(Restrictions.eq("thesaurusArray.identifier", thesaurusArrayId));
		List<NodeLabel> foundNodeLabels = criteria.list();

		if (foundNodeLabels.size() > 0) {
			return (NodeLabel) criteria.list().get(0);
		}
		return null;
	}
}