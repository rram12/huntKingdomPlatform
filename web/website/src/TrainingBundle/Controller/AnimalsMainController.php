<?php

namespace TrainingBundle\Controller;

use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use TrainingBundle\Entity\Animal;
use TrainingBundle\Entity\Entrainement;
use TrainingBundle\Form\AnimalType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\File\File;
class AnimalsMainController extends Controller
{
    public function ajouterAction(Request $request)
    {

        $animal = new Animal();
        $form = $this->createForm(AnimalType::class, $animal);
        $form->handleRequest($request);
        //$categorie = $request->get('categorie');
        //$debutSaison = $request->get('debutSaison');
        //$finSaison = $request->get('finSaison');
        if ($form->isSubmitted()) {

            //$animal->setCategorie($categorie);
            //$animal->setDebutSaison($debutSaison);
            //$animal->setFinSaison($finSaison);
            $em = $this->getDoctrine()->getManager();
            if ($animal->getImageAnimal() !== null){
                $file = $animal->getImageAnimal();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('photos_directory'), $filename);
                $animal->setImageAnimal($filename);
            }
            else{
                $animal->setImageAnimal(' ');
            }
            $em->persist($animal);
            $em->flush();
            return $this->redirectToRoute('list_animals');
        };
        return $this->render("@Training/dashboard/ajouter_animals.html.twig", array('form' => $form->createView()));

    }

    public function listDashAction()
    {
        $em = $this->getDoctrine()->getManager();
        $animals = $em->getRepository("TrainingBundle:Animal")->findAll();
        return $this->render("@Training/dashboard/list_animals.html.twig", array('animals' => $animals));
    }

    public function listHuntingFrontAction($start,$end)
    {
$em = $this->getDoctrine()->getManager();
        if($start==0 && $end == 13){
            $animals = $em->getRepository("TrainingBundle:Animal")->findAnimalHuntingAll();

            return $this->render("@Training/front/animalsHunting.html.twig", array('animals' => $animals));
        }else{
            $animals = $em->getRepository("TrainingBundle:Animal")->findAnimalHunting($start,$end);
            if(!$animals) {
            $result['animals']['error'] = "not found !";
        } else {
            $result['animals'] = $this->getRealEntities($animals);
        }
            return new Response(json_encode($result));
        }
    }

    public function getRealEntities($animals){
        $i=0;
        foreach ($animals as $animal){
            $realEntities[$i] =array(
                'categorie'=>$animal->getCategorie(),
                'nom'=>$animal->getNom(),
                'description'=>$animal->getDescription(),
                'image_animal'=>$animal->getImageAnimal(),
                'debutSaison'=>$animal->getDebutSaison(),
                'finSaison'=>$animal->getFinSaison(),

            );
            $i++;
        }
        return $realEntities;
    }






    public function listFishingFrontAction()
    {
        $em = $this->getDoctrine()->getManager();
        $animals = $em->getRepository("TrainingBundle:Animal")->findAnimalFishing();
        return $this->render("@Training/front/animalsFishing.html.twig", array('animals' => $animals));
    }

    function UpdateAnimalAction($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $animal = $em->getRepository(Animal::class)->find($id);
        $animal->setImageAnimal(null);
        $Form = $this->createForm(AnimalType::class, $animal);
        $Form->handleRequest($request);

        if ($Form->isSubmitted()) {
            if ($animal->getImageAnimal() !== null){
                $file = $animal->getImageAnimal();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('photos_directory'), $filename);
                $animal->setImageAnimal($filename);
            }
            else{
                $animal->setImageAnimal(' ');
            }

            $em->flush();
            return $this->redirectToRoute('list_animals');
        }
        return $this->render('@Training/dashboard/update_animals.html.twig', array('form' => $Form->createView()));
    }

    function DeleteAnimalAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $animal = $em->getRepository(Animal::class)->find($id);
        $em->remove($animal);
        $em->flush();
        return $this->redirectToRoute('list_animals');
    }

    /*function SearchAction(Request $request)
    {
        $em = $this->getDoctrine();
        $tab = $em->getRepository(Animal::class)->findAll();

        $input = $request->get('Nom');
        if (isset($input)) {
            $animals = $em->getRepository(Animal::class)->findNom($input);

            return $this->render('@Training/dashboard/list_animals.html.twig', array(
                'animals' => $animals
            ));
        }

        return $this->render('@Training/dashboard/list_animals.html.twig', array(
            'animals' => $tab
        ));
    }*/


    public function rechercheEvenementAction(Request $request)
    {
        $event=$request->get('animal');

        $events=$this->getDoctrine()->getManager()->createQuery("select e from TrainingBundle:Animal e where e.nom like '%".$event."%' or e.debutSaison like '%".$event."%' or e.finSaison like '%".$event."%'or e.image_animal like '%".$event."%' ")
            ->getResult();

        //die("aa");
        $jsonData=array();
        $idx=0;
        foreach ($events as $liv) {
            $temp=array(
                'id'=>$liv->getId(),
                'nom'=>$liv->getNom(),
                'categorie'=>$liv->getCategorie(),
                'description'=>$liv->getDescription(),
                'image_animal'=>$liv->getImageAnimal(),
                'debutSaison'=>$liv->getDebutSaison(),
                'finSaison'=>$liv->getFinSaison(),


            );
            $jsonData[$idx++]=$temp;

        }

        return new JsonResponse($jsonData);

        //return
    }




}
