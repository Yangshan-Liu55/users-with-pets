export type UserWithPet = {
  id: string
  gender?: string
  country?: string
  name?: string
  email?: string
  dob?: { date: string; age: number } | any
  phone?: string
  petImage?: string
}

export async function fetchUsers(count: number, nat: string): Promise<UserWithPet[]> {
  const params = new URLSearchParams()
  params.append('count', String(count))
  if (nat) params.append('nat', nat)

  const res = await fetch(`/api/users-with-pet?${params.toString()}`)
  if (!res.ok) {
    const body = await res.text().catch(() => '')
    throw new Error(`API error ${res.status}: ${body || res.statusText}`)
  }
  let data = await res.json()
  return data as UserWithPet[]
}
