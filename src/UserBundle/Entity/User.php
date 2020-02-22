<?php
// src/UserBundle/Entity/User.php

namespace UserBundle\Entity;
use EventsBundle\Entity\Competition;
use Doctrine\Common\Collections\ArrayCollection;
use FOS\UserBundle\Model\User as BaseUser;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
/**
 * @ORM\Entity
 * @ORM\Table(name="fos_user")
 * @Vich\Uploadable
 */
class User extends BaseUser
{
    /**
     * Many Users have Many Groups.
     * @ORM\ManyToMany(targetEntity="EventsBundle\Entity\Competition", inversedBy="users")
     * @ORM\JoinTable(name="ParticipationCompetition")
     */
    private $competitions;

    public function __construct() {
        parent::__construct();
        $this->competitions = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * @ORM\Id
     * @ORM\Column(type="integer")
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;
    /**
     * @ORM\Column(type="string", length=255 ,nullable=true )
     * @var string
     */
    private $contract = null;

    /**
     * @return string
     */
    public function getContract()
    {
        return $this->contract;
    }

    /**
     * @param string $contract
     */
    public function setContract($contract)
    {
        $this->contract = $contract;
    }

    /**
     * @return File
     */
    public function getContractFile()
    {
        return $this->contractFile;
    }

    /**
     * @param File $contractFile
     */
    public function setContractFile($contractFile)
    {
        $this->contractFile = $contractFile;
    }

    /**
     * @Vich\UploadableField(mapping="user_contracts", fileNameProperty="contract")
     * @var File
     */
    private $contractFile;

    /**
     * @return ArrayCollection
     */
    public function getCompetitions()
    {
        return $this->competitions;
    }

    /**
     * @param ArrayCollection $competitions
     */
    public function setCompetitions($competitions)
    {
        $this->competitions = $competitions;
    }

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @var boolean
     *
     * @ORM\Column(name="confirmed", type="boolean",options={"default": false})
     */
    private $confirmed =false ;

    /**
     * @return bool
     */
    public function isConfirmed()
    {
        return $this->confirmed;
    }

    /**
     * @param bool $confirmed
     */
    public function setConfirmed($confirmed)
    {
        $this->confirmed = $confirmed;
    }

    /**
     * @var string
     *
     * @ORM\Column(name="firstName", type="string", length=255)
     */
    protected $firstName;

    /**
     * @var string
     *
     * @ORM\Column(name="lastName", type="string", length=255)
     */
    protected $lastName;

    /**
     * @var string
     *
     * @ORM\Column(name="address", type="string", length=255)
     */
    protected $address;

    /**
     * @var int
     *
     * @ORM\Column(name="phoneNumber", type="integer")
     */
    protected $phoneNumber;

    /**
     * @var string
     * @Assert\File(mimeTypes={ "image/png", "image/jpeg" })
     * @ORM\Column(name="picture", type="string", length=255 ,nullable=true)
     */
    protected $picture;


    /**
     * @var boolean
     *
     * @ORM\Column(name="gender", type="boolean")
     */
    protected $gender;

    /**
     * @return string
     */
    public function getAddress()
    {
        return $this->address;
    }

    /**
     * @param string $address
     */
    public function setAddress($address)
    {
        $this->address = $address;
    }





    /**
     * @return string
     */
    public function getFirstName()
    {
        return $this->firstName;
    }

    /**
     * @param string $firstName
     */
    public function setFirstName($firstName)
    {
        $this->firstName = $firstName;
    }

    /**
     * @return string
     */
    public function getGender()
    {
        return $this->gender;
    }

    /**
     * @param string $gender
     */
    public function setGender($gender)
    {
        $this->gender = $gender;
    }

    /**
     * @return string
     */
    public function getLastName()
    {
        return $this->lastName;
    }

    /**
     * @param string $lastName
     */
    public function setLastName($lastName)
    {
        $this->lastName = $lastName;
    }

    /**
     * @return int
     */
    public function getPhoneNumber()
    {
        return $this->phoneNumber;
    }

    /**
     * @param int $phoneNumber
     */
    public function setPhoneNumber($phoneNumber)
    {
        $this->phoneNumber = $phoneNumber;
    }

    /**
     * @return string
     */
    public function getPicture()
    {
        return $this->picture;
    }

    /**
     * @param string $picture
     */
    public function setPicture($picture)
    {
        $this->picture = $picture;
    }

}
