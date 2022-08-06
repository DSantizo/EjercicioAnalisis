/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.Language;
import fr.mcc.ginco.dao.ILanguageDAO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class LanguageDAO extends GenericHibernateDAO<Language, String>
		implements ILanguageDAO {

	private Logger logger = LoggerFactory.getLogger(LanguageDAO.class);


	public LanguageDAO() {
		super(Language.class);
	}

	/**
	 * @return List of Language with favorites Language first, and the other
	 * elements sorted alphabetically with a starting index and a limit
	 * of items to be returned
	 */
	@Override
	public List<Language> findPaginatedItems(Integer start, Integer limit) {
		return getCurrentSession().createCriteria(Language.class)
				.setMaxResults(limit).setFirstResult(start)
				.addOrder(Order.desc("topLanguage"))
				.addOrder(Order.asc("refname")).list();
	}

	/**
	 * @return List of top Languages
	 */
	@Override
	public List<Language> findTopLanguages() {
		return getCurrentSession().createCriteria(Language.class)
				.add(Restrictions.eq("topLanguage", true))
				.addOrder(Order.asc("refname")).list();
	}


	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.ILanguageDAO#getByPart1(java.lang.String)
	 */
	@Override
	public Language getByPart1(String part1) {
		List<Language> languages = getCurrentSession()
				.createCriteria(Language.class)
				.add(Restrictions.eq("part1", part1))
				.add(Restrictions.eq("principalLanguage", true)).list();
		if (languages != null && languages.size() > 0) {
			if (languages.size() > 01) {
				logger.warn("Multiple principal languages found for the same part1 " + part1);
			}
			return languages.get(0);
		}
		return null;
	}

}