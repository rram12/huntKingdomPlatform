<?php

namespace ProductBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Panier
 *
 * @ORM\Table(name="panier")
 * @ORM\Entity(repositoryClass="ProductBundle\Repository\PanierRepository")
 */
class Panier
{
    /**
     * Many Groups have Many Users.
     * @ORM\ManyToMany(targetEntity="Produit", mappedBy="paniers")
     */
    private $produits;

    public function __construct() {
        $this->produits = new \Doctrine\Common\Collections\ArrayCollection();
    }
    public function addtopanier($produit) {
        $this->produits->add($produit);

    }
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="datePanier", type="date")
     */
    private $datePanier;

    /**
     * @var float
     *
     * @ORM\Column(name="prixTotal", type="float")
     */
    private $prixTotal;

    /**
     * @var int
     *
     * @ORM\Column(name="nbArticles", type="integer")
     */
    private $nbArticles;

    /**
     * @return float
     */
    public function getPrixTotal()
    {
        return $this->prixTotal;
    }

    /**
     * @param float $prixTotal
     */
    public function setPrixTotal($prixTotal)
    {
        $this->prixTotal = $prixTotal;
    }

    /**
     * @return int
     */
    public function getNbArticles()
    {
        return $this->nbArticles;
    }

    /**
     * @param int $nbArticles
     */
    public function setNbArticles($nbArticles)
    {
        $this->nbArticles = $nbArticles;
    }

    /**
     * @return string
     */

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
     * Set datePanier
     *
     * @param \DateTime $datePanier
     *
     * @return Panier
     */
    public function setDatePanier($datePanier)
    {
        $this->datePanier = $datePanier;

        return $this;
    }

    /**
     * @return string
     */
    /**
     * Get datePanier
     *
     * @return \DateTime
     */
    public function getDatePanier()
    {
        return $this->datePanier;
    }
}

