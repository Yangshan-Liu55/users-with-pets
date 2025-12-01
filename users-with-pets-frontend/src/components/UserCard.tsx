import { ImCalendar, ImMail, ImPhone } from 'react-icons/im'
import type { UserWithPet } from '../api'

export default function UserCard({ user }: { user: UserWithPet }) {
  return (
    <article className="card" aria-labelledby={`user-${user.id}`}>
      <div className="image-wrap">
        {user.petImage ? <img src={user.petImage} alt={`${user.name}'s dog`} /> : <div className="no-image">No image</div>}
      </div>
      <div className="card-body">
        <h3 id={`user-${user.id}`}>{user.name ?? 'Unknown'}</h3>
        <p className='country'>{user.country ?? '-'}</p>
        <p><ImMail /> {user.email ?? '-'}</p>
        <p><ImPhone /> {user.phone ?? '-'}</p>
        {user.dob && user.dob['date'] ? <p ><ImCalendar /> {new Date(user.dob['date']).toISOString().split('T')[0]} </p> : ''}
      </div>
    </article>
  )
}
