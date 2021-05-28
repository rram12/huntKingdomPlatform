<?php

namespace ServiceBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * MoyenDeTransport
 *
 * @ORM\Table(name="moyen_de_transport")
 * @ORM\Entity(repositoryClass="ServiceBundle\Repository\MoyenDeTransportRepository")
 */
class MoyenDeTransport
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
     * @return string
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * @param string $type
     */
    public function setType($type)
    {
        $this->type = $type;
    }

    /**
     * @return float
     */
    public function getPrixParJour()
    {
        return $this->prixParJour;
    }

    /**
     * @param float $prixParJour
     */
    public function setPrixParJour($prixParJour)
    {
        $this->prixParJour = $prixParJour;
    }

    /**
     * @return bool
     */
    public function isDisponibilite()
    {
        return $this->disponibilite;
    }

    /**
     * @param bool $disponibilite
     */
    public function setDisponibilite($disponibilite)
    {
        $this->disponibilite = $disponibilite;
    }

    /**
     * @return string
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * @param string $image
     */
    public function setImage($image)
    {
        $this->image = $image;
    }

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


    /**
     * @var string
     *
     * @ORM\Column(name="marque", type="string", length=255)
     */
    private $marque;

    /**
     * @var string
     *
     * @ORM\Column(name="categorie", type="string", length=255)
     */
    private $categorie;


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
     * Set marque
     *
     * @param string $marque
     *
     * @return MoyenDeTransport
     */
    public function setMarque($marque)
    {
        $this->marque = $marque;

        return $this;
    }

    /**
     * Get marque
     *
     * @return string
     */
    public function getMarque()
    {
        return $this->marque;
    }

    /**
     * Set categorie
     *
     * @param string $categorie
     *
     * @return MoyenDeTransport
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
}

