<?php

namespace ProductBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Produit
 *
 * @ORM\Table(name="produit")
 * @ORM\Entity(repositoryClass="ProductBundle\Repository\ProduitRepository")
 */

class Produit
{
    /**
     * Many Users have Many Groups.
     * @ORM\ManyToMany(targetEntity="Panier", inversedBy="produits")
     * @ORM\JoinTable(name="commandes")
     */
    private $paniers;

    /**
     * @ORM\ManyToOne(targetEntity="Marque")
     * @ORM\JoinColumn(name="marqueId", referencedColumnName="id")
     */
    private $marque;


    public function __construct() {
        $this->paniers = new \Doctrine\Common\Collections\ArrayCollection();
    }
    /**
     * @ORM\ManyToOne(targetEntity="Promotion")
     * @ORM\JoinColumn(name="promotion_id", referencedColumnName="id")
     */
    private $promotion;

    /**
     * @return \Doctrine\Common\Collections\ArrayCollection
     */
    public function getPaniers()
    {
        return $this->paniers;
    }

    /**
     * @param \Doctrine\Common\Collections\ArrayCollection $paniers
     */
    public function setPaniers($paniers)
    {
        $this->paniers = $paniers;
    }

    /**
     * @return mixed
     */
    public function getPromotion()
    {
        return $this->promotion;
    }





    /**
     * @return mixed
     */
    public function getMarque()
    {
        return $this->marque;
    }

    /**
     * @param mixed $marque
     */
    public function setMarque($marque)
    {
        $this->marque = $marque;
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
     * @var string
     *
     * @ORM\Column(name="lib_prod", type="string", length=255)
     */
    private $libProd;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float")
     */
    private $prix;

    /**
     * @var float
     *
     * @ORM\Column(name="prixFinale", type="float")
     */
    private $prixFinale;

    /**
     * @return float
     */
    public function getPrixFinale()
    {
        return $this->prixFinale;
    }

    /**
     * @param float $prixFinale
     */
    public function setPrixFinale($prixFinale)
    {
        $this->prixFinale = $prixFinale;
    }

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @var int
     *
     * @ORM\Column(name="qte_prod", type="integer")
     */
    private $qteProd;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_ajout", type="date")
     */
    private $dateAjout;

    /**
     * @var string
     * @Assert\File(mimeTypes={ "image/png", "image/jpeg" })
     * @ORM\Column(name="image", type="string", length=255)
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255)
     */
    private $type;


}

