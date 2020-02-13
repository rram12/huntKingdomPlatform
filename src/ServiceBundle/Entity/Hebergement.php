<?php

namespace ServiceBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Hebergement
 *
 * @ORM\Table(name="hebergement")
 * @ORM\Entity(repositoryClass="ServiceBundle\Repository\HebergementRepository")
 */
class Hebergement
{
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
     * @ORM\Column(name="type", type="string", length=255)
     */
    private $type;

    /**
     * @var float
     *
     * @ORM\Column(name="prixParJour", type="float")
     */
    private $prixParJour;

    /**
     * @var bool
     *
     * @ORM\Column(name="disponibilite", type="boolean")
     */
    private $disponibilite;

    /**
     * @var string
     *
     * @ORM\Column(name="image", type="string", length=255)
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=255)
     */
    private $adresse;

    /**
     * @var int
     *
     * @ORM\Column(name="nbChambre", type="integer")
     */
    private $nbChambre;

    /**
     * @var int
     *
     * @ORM\Column(name="nbLits", type="integer")
     */
    private $nbLits;

    /**
     * @var int
     *
     * @ORM\Column(name="capacite", type="integer")
     */
    private $capacite;

    /**
     * @return string
     */
    public function getAdresse()
    {
        return $this->adresse;
    }

    /**
     * @param string $adresse
     */
    public function setAdresse($adresse)
    {
        $this->adresse = $adresse;
    }

    /**
     * @return int
     */
    public function getNbChambre()
    {
        return $this->nbChambre;
    }

    /**
     * @param int $nbChambre
     */
    public function setNbChambre($nbChambre)
    {
        $this->nbChambre = $nbChambre;
    }

    /**
     * @return int
     */
    public function getNbLits()
    {
        return $this->nbLits;
    }

    /**
     * @param int $nbLits
     */
    public function setNbLits($nbLits)
    {
        $this->nbLits = $nbLits;
    }

    /**
     * @return int
     */
    public function getCapacite()
    {
        return $this->capacite;
    }

    /**
     * @param int $capacite
     */
    public function setCapacite($capacite)
    {
        $this->capacite = $capacite;
    }

    /**
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription($description)
    {
        $this->description = $description;
    }

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;
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
     * Set type
     *
     * @param string $type
     *
     * @return Service
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

    /**
     * Set prixParJour
     *
     * @param float $prixParJour
     *
     * @return Service
     */
    public function setPrixParJour($prixParJour)
    {
        $this->prixParJour = $prixParJour;

        return $this;
    }

    /**
     * Get prixParJour
     *
     * @return float
     */
    public function getPrixParJour()
    {
        return $this->prixParJour;
    }

    /**
     * Set disponibilite
     *
     * @param boolean $disponibilite
     *
     * @return Service
     */
    public function setDisponibilite($disponibilite)
    {
        $this->disponibilite = $disponibilite;

        return $this;
    }

    /**
     * Get disponibilite
     *
     * @return bool
     */
    public function getDisponibilite()
    {
        return $this->disponibilite;
    }

    /**
     * Set image
     *
     * @param string $image
     *
     * @return Service
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
}

