<?php

namespace ProductBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

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

    public function __construct() {
        $this->paniers = new \Doctrine\Common\Collections\ArrayCollection();
    }
    /**
     * @ORM\ManyToOne(targetEntity="Promotion")
     * @ORM\JoinColumn(name="promotion_id", referencedColumnName="id")
     */
    private $promotion;

    /**
     * @ORM\ManyToOne(targetEntity="Marque")
     * @ORM\JoinColumn(name="marqueId", referencedColumnName="id")
     */
    private $marque;

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
     * @param mixed $promotion
     */
    public function setPromotion($promotion)
    {
        $this->promotion = $promotion;
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
     *
     * @ORM\Column(name="image", type="string", length=255)
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255)
     */
    private $type;


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
     * Set libProd
     *
     * @param string $libProd
     *
     * @return Product
     */
    public function setLibProd($libProd)
    {
        $this->libProd = $libProd;

        return $this;
    }

    /**
     * Get libProd
     *
     * @return string
     */
    public function getLibProd()
    {
        return $this->libProd;
    }

    /**
     * Set prix
     *
     * @param float $prix
     *
     * @return Product
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
     * Set description
     *
     * @param string $description
     *
     * @return Product
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set qteProd
     *
     * @param integer $qteProd
     *
     * @return Product
     */
    public function setQteProd($qteProd)
    {
        $this->qteProd = $qteProd;

        return $this;
    }

    /**
     * Get qteProd
     *
     * @return int
     */
    public function getQteProd()
    {
        return $this->qteProd;
    }

    /**
     * Set dateAjout
     *
     * @param \DateTime $dateAjout
     *
     * @return Product
     */
    public function setDateAjout($dateAjout)
    {
        $this->dateAjout = $dateAjout;

        return $this;
    }

    /**
     * Get dateAjout
     *
     * @return \DateTime
     */
    public function getDateAjout()
    {
        return $this->dateAjout;
    }

    /**
     * Set image
     *
     * @param string $image
     *
     * @return Product
     */
    public function setImage($image)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * Get image
     *
     * @return string
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * Set type
     *
     * @param string $type
     *
     * @return Product
     */
    public function setType($type)
    {
        $this->type = $type;

        return $this;
    }

    /**
     * Get type
     *
     * @return string
     */
    public function getType()
    {
        return $this->type;
    }
}

