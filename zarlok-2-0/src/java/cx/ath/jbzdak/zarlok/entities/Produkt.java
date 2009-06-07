package cx.ath.jbzdak.zarlok.entities;

import cx.ath.jbzdak.zarlok.entities.listeners.ProductSearchCacheUpdater;
import javax.annotation.Nonnull;
import javax.persistence.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.Length;
import org.hibernate.validator.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="PRODUKT")
@NamedQueries({
	@NamedQuery(
		name="getProduktNazwa",
		query = "SELECT DISTINCT p.nazwa FROM Produkt p WHERE p.nazwa LIKE CONCAT(CONCAT('%', :nazwa), '%')"
	),
	@NamedQuery(
		name="getProduktByNazwa",
		query = "SELECT p FROM Produkt p WHERE p.nazwa LIKE CONCAT(CONCAT('%', :nazwa), '%')"
	),
	@NamedQuery(
			name="getProduktJednostka",
			query = "SELECT DISTINCT p.jednostka FROM Produkt p WHERE LOWER(p.jednostka) LIKE LOWER(CONCAT(CONCAT('%', :jednostka), '%'))"
	),
	@NamedQuery(
			name="countProduktNazwa",
			query = "SELECT COUNT(p) FROM Produkt p WHERE p.nazwa = :nazwa"
	),
   @NamedQuery(
           name = "getStanMagazynu",
           query = "SELECT new cx.ath.jbzdak.zarlok.raport.stany.StanMagazynuEntryBean(\n" +
                   " p,\n " +
                   " SUM(w.iloscJednostek)" +
                   ") FROM Partia p, IN(p.wyprowadzenia) w\n" +
                   "WHERE p.dataKsiegowania <= :data AND p.dataWaznosci > :data\n " +
                   "AND w.dataWyprowadzenia < :data GROUP BY p, p.iloscPocz \n"

   ),@NamedQuery(
           name = "getStanMagazynu2",
           query = "SELECT new cx.ath.jbzdak.zarlok.raport.stany.StanMagazynuEntryBean(" +
                   " p " +
                   ") FROM Partia p\n" +
                   "WHERE p.dataKsiegowania <= :data AND (p.dataWaznosci IS NULL OR p.dataWaznosci > :data)\n " +
                   "AND (SELECT COUNT(w) FROM Wyprowadzenie w WHERE w.dataWyprowadzenia < :data AND w.partia = p) = 0"

   )
})
@EntityListeners({ProductSearchCacheUpdater.class})
public class Produkt {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@Length(min=1, max=50)
	@Column(name="NAZWA", unique=true, nullable=false)
	private String nazwa;

	@Length(min=1, max=50)
	@Column(name="JEDNOSTKA")
	private String jednostka;

	/**
	 * Data ważności zero znaczy: nieustalona
	 * -1 nieskończona
	 */
	@Nonnull
	@Range(min=-1)
	@Column(name="DATA_WAZNOSCI", nullable = false)
	private Integer dataWaznosci;

	@OneToMany(mappedBy="produkt", cascade=CascadeType.ALL)
   private
	List<Partia> partie = new ArrayList<Partia>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getJednostka() {
		return jednostka;
	}

	public void setJednostka(String jednostka) {
		this.jednostka = jednostka;
	}

	public Integer getDataWaznosci() {
		return dataWaznosci;
	}

	public void setDataWaznosci(Integer dataWaznosci) {
		this.dataWaznosci = dataWaznosci;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public List<Partia> getPartie() {
		return partie;
	}

	public void setPartie(List<Partia> partie) {
		this.partie = partie;
	}
}
