<?php

namespace TrainingBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Entrainement
 *
 * @ORM\Table(name="entrainement")
 * @ORM\Entity(repositoryClass="TrainingBundle\Repository\EntrainementRepository")
 */
class Entrainement
{
    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="userId", referencedColumnName="id")
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\Trainer")
     * @ORM\JoinColumn(name="entraineurId", referencedColumnName="id")
     */
    private $entraineur;

    /**
     * @ORM\ManyToOne(targetEntity="Animal")
     * @ORM\JoinColumn(name="animalId", referencedColumnName="id")
     */
    private $animal;

    /**
     * @ORM\ManyToOne(targetEntity="ProductBundle\Entity\Produit")
     * @ORM\JoinColumn(name="produitId", referencedColumnName="id")
     */
    private $produit;

    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="categorie", type="string", length=255)
     */
    private $categorie;

    /**
     * @var int
     *
     * @ORM\Column(name="nbHeures", type="integer")
     */
    private $nbHeures;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateEnt", type="date")
     */
    private $dateEnt;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float")
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="lieu", type="string", length=255)
     */
    private $lieu;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set categorie
     *
     * @param string $categorie
     *
     * @return Entrainement
     */
    public function setCategorie($categorie)
    {
        $this->categorie = $categorie;

        return $this;
    }

    /**
     * Get categorie
     *
     * @return string
     */
    public function getCategorie()
    {
        return $this->categorie;
    }

    /**
     * Set nbHeures
     *
     * @param integer $nbHeures
     *
     * @return Entrainement
     */
    public function setNbHeures($nbHeures)
    {
        $this->nbHeures = $nbHeures;

        return $this;
    }

    /**
     * Get nbHeures
     *
     * @return int
     */
    public function getNbHeures()
    {
        return $this->nbHeures;
    }

    /**
     * Set dateEnt
     *
     * @param \DateTime $dateEnt
     *
     * @return Entrainement
     */
    public function setDateEnt($dateEnt)
    {
        $this->dateEnt = $dateEnt;

        return $this;
    }

    /**
     * Get dateEnt
     *
     * @return \DateTime
     */
    public function getDateEnt()
    {
        return $this->dateEnt;
    }

    /**
     * Set prix
     *
     * @param float $prix
     *
     * @return Entrainement
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * Get prix
     *
     * @return float
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * Set lieu
     *
     * @param string $lieu
     *
     * @return Entrainement
     */
    public function setLieu($lieu)
    {
        $this->lieu = $lieu;

        return $this;
    }

    /**
     * Get lieu
     *
     * @return string
     */
    public function getLieu()
    {
        return $this->lieu;
    }
}

