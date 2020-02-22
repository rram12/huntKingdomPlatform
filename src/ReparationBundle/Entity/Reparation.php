<?php

namespace ReparationBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reparation
 *
 * @ORM\Table(name="reparation")
 * @ORM\Entity(repositoryClass="ReparationBundle\Repository\ReparationRepository")
 */
class Reparation
{


    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="userId", referencedColumnName="id")
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity="Piecesdefectueuses")
     * @ORM\JoinColumn(name="Piecesdefectueuses_id", referencedColumnName="id")
     */
    private $piecedefectueuse;


    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @return mixed
     */
    public function getUser()
    {
        return $this->user;
    }

    /**
     * @param mixed $user
     */
    public function setUser($user)
    {
        $this->user = $user;
    }

    /**
     * @return \DateTime
     */
    public function getDateFin()
    {
        return $this->dateFin;
    }

    /**
     * @param \DateTime $dateFin
     */
    public function setDateFin($dateFin)
    {
        $this->dateFin = $dateFin;
    }
    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateFin", type="datetime")
     */
    private $dateFin;

    /**
     * @var float
     *
     * @ORM\Column(name="prixRep", type="float")
     */
    private $prixRep;





    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @return mixed
     */
    public function getPiecedefectueuse()
    {
        return $this->piecedefectueuse;
    }

    /**
     * @param mixed $piecedefectueuse
     */
    public function setPiecedefectueuse($piecedefectueuse)
    {
        $this->piecedefectueuse = $piecedefectueuse;
    }


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
     * Set prixRep
     *
     * @param float $prixRep
     *
     * @return Reparation
     */
    public function setPrixRep($prixRep)
    {
        $this->prixRep = $prixRep;

        return $this;
    }

    /**
     * Get prixRep
     *
     * @return float
     */
    public function getPrixRep()
    {
        return $this->prixRep;
    }






    /**
     * Set description
     *
     * @param string $description
     *
     * @return Reparation
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



}

