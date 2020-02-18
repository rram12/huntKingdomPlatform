<?php

namespace EventsBundle\Controller;

use EventsBundle\Entity\Competition;
<<<<<<< HEAD
use EventsBundle\Entity\Publicity;
use EventsBundle\Form\CompetitionType;
use EventsBundle\Form\PublicityType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class EventController extends Controller
{
    public function affichercompetitionAction()
    {
        $Competition = new Competition();
=======
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class EventController extends Controller
{
    public function affichercompetitionAction() {
        $Article = new Competition();
>>>>>>> 3b06f85ee52bf7b8beae829bd173eb21b1399ca7
        $em = $this->getDoctrine()->getManager();
        $competitions = $em->getRepository('EventsBundle:Competition')->findAll();

        return $this->render('@Events/dashboard/competition.html.twig', array('competitions' => $competitions));
    }
<<<<<<< HEAD

    public function competitionajoutAction(Request $request)
    {
        $competition = new Competition();
        $form_ajout = $this->createForm(CompetitionType::class, $competition);
        $form_ajout->handleRequest($request);
        if ($form_ajout->isSubmitted()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($competition);
            $em->flush();
            return $this->redirect($this->generateUrl('list_competition'));
        }
        return $this->render('@Events/dashboard/competitionajout.html.twig', array('form' => $form_ajout->createView()));
    }

    public function afficherpubliciteAction()
    {
        $Publicity = new Publicity();
        $em = $this->getDoctrine()->getManager();
        $publicites = $em->getRepository('EventsBundle:Publicity')->findAll();

        return $this->render('@Events/dashboard/publicity.html.twig', array('publicites' => $publicites));
    }

    public function publicityajoutAction(Request $request)
    {
        $publicity = new Publicity();
        $form = $this->createForm(PublicityType::class, $publicity);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager();
            if ($publicity->getImage() !== null){
                $file = $publicity->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $publicity->setImage($filename);
            }
            else{
                $publicity->setImage(' ');
            }
            $em->persist($publicity);
            $em->flush();
            return $this->redirectToRoute('list_publicity');
        }
        return $this->render('@Events/dashboard/publicityajout.html.twig', array('form' => $form->createView()));
    }

    public function supprimerpublicityAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $publicity = $em->getRepository("EventsBundle:Publicity")->find($id);
        $em->remove($publicity);
        $em->flush();
        return $this->redirectToRoute("list_publicity");
    }

    public function modifierpublicityAction(Request $request, $id)
    {

        $publicity = new Publicity();
        $em = $this->getDoctrine()->getManager();
        $publicity = $em->getRepository(Publicity::class)->find($id);
        $form = $this->createForm(PublicityType::class, $publicity);

        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('list_publicity');
        }

        return $this->render('@Events/dashboard/publicitymodifier.html.twig', array('form' => $form->createView()));

    }

    public function addtocompetitionAction(Request $request, $id)
    {
        $competition = $this->getDoctrine()->getRepository('EventsBundle:Competition')->find($id);
        $user = $this->getUser();

        $competition->addtocompetition($user);
        $em = $this->getDoctrine()->getManager();
        $em->persist($competition);
        $em->flush();
        return $this->redirectToRoute("list_event");
    }

    public function supprimercompetitionAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $competition = $em->getRepository("EventsBundle:Competition")->find($id);
        $em->remove($competition);
        $em->flush();
        return $this->redirectToRoute("list_competition");
    }

    public function modifiercompetitionAction(Request $request, $id)
    {
        $competition = new Competition();
        $em = $this->getDoctrine()->getManager();
        $competition = $em->getRepository(Competition::class)->find($id);
        $form = $this->createForm(CompetitionType::class, $competition);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('list_competition');
        }
        return $this->render('@Events/dashboard/competitionmodifier.html.twig', array('form' => $form->createView()));
    }
    public function listcompetitionAction()
    {
        $em = $this->getDoctrine()->getManager();
        $competitions = $em->getRepository('EventsBundle:Competition')->findAll();

        return $this->render('@Events/front/event.html.twig', array('competitions' => $competitions));
    }
    public function listPublicitesAction()
    {
        $em = $this->getDoctrine()->getManager();
        $publicites = $em->getRepository('EventsBundle:Publicity')->findAll();

        return $this->render('@Events/front/event-single.html.twig', array('publicites' => $publicites));
    }
}


=======
}
>>>>>>> 3b06f85ee52bf7b8beae829bd173eb21b1399ca7
